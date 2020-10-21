package com.ihomefnt.o2o.service.service.art;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.art.dto.*;
import com.ihomefnt.o2o.intf.domain.art.vo.request.*;
import com.ihomefnt.o2o.intf.domain.art.vo.response.*;
import com.ihomefnt.o2o.intf.domain.cart.dto.AjbAccountDto;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.art.IhomeMallProxy;
import com.ihomefnt.o2o.intf.proxy.cart.ShoppingCartProxy;
import com.ihomefnt.o2o.intf.service.art.IhomeMallService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wanyunxin
 * @create 2019-08-06 19:24
 */
@Service
public class IhomeMallServiceImpl implements IhomeMallService {

    @NacosValue(value = "${app.aimall.banner}", autoRefreshed = true)
    private String ihomeMallBanner;

    @Autowired
    IhomeMallProxy ihomeMallProxy;

    @Autowired
    private ShoppingCartProxy shopppingCartProxy;

    /**
     * 艾积分汇率
     */
    private static int EX_RATE = 100;

    /**
     * 艾商城首页接口
     * @param width
     * @return
     */
    @Override
    public ArtMallResponse queryArtMall(Integer width,Integer osType) {

        //banner列表
        List<ArtBannerDto> bannerList = JsonUtils.json2list(ihomeMallBanner, ArtBannerDto.class);

        Map<String, Object> stringObjectMap = concurrentQueryArtMallList();

        //新艾商城艺术品app端分类列表
        List<FrontCategoryInfoDto> frontCategoryInfoList =  (List<FrontCategoryInfoDto> )stringObjectMap.get(ConcurrentTaskEnum.QUERY_FRONT_CATEGORY_LIST.name());

        //艺术家推荐列表
        List<ArtistRecommendInfoDto> artistRecommendInfoList =  (List<ArtistRecommendInfoDto> )stringObjectMap.get(ConcurrentTaskEnum.QUERY_ARTIST_RECOMMEND_LIST.name());

        //艺术作品列表
        List<ArtDto> artList =  (List<ArtDto> )stringObjectMap.get(ConcurrentTaskEnum.QUERY_ART_LIST.name());

        //前端分类切图
        setFrontCategoryInfoListCut(frontCategoryInfoList,width,osType);

        //艺术家推荐列表切图
        artistRecommendInfoListCut(artistRecommendInfoList,width,osType);

        //艺术品列表切图
        artListCut(artList,width,osType);

        return new ArtMallResponse(bannerList,frontCategoryInfoList,artistRecommendInfoList,artList);
    }

    /**
     * 定制品列表展示
     * @param request
     * @return
     */
    @Override
    public FrontCategoryDto queryFrontCategoryList(CategoryArtPageRequest request) {
        FrontCategoryDto frontCategoryDto = ihomeMallProxy.queryFrontCategoryList(request);
        List<FrontCategoryInfoDto> frontCategoryInfo = frontCategoryDto.getFrontCategoryInfo();
        setFrontCategoryInfoListCut(frontCategoryInfo,request.getWidth(),request.getOsType());
        return frontCategoryDto;
    }


    /**
     * 前端分类切图
     * @param frontCategoryInfoList
     */
    private void setFrontCategoryInfoListCut(List<FrontCategoryInfoDto> frontCategoryInfoList,Integer width,Integer osType){
        if(CollectionUtils.isNotEmpty(frontCategoryInfoList)){
            frontCategoryInfoList.forEach(frontCategoryInfoDto -> {
                frontCategoryInfoDto.setFrontCategoryIcon(AliImageUtil.imageCompress(frontCategoryInfoDto.getFrontCategoryIcon(),
                        osType,width, ImageConstant.SIZE_SMALL));
                frontCategoryInfoDto.setFrontCategoryImage(AliImageUtil.imageCompress(frontCategoryInfoDto.getFrontCategoryImage(),
                        osType,width, ImageConstant.SIZE_MIDDLE));
            });
        }
    }

    /**
     * 艺术家推荐列表切图
     * @param artistRecommendInfoList
     */
    private void artistRecommendInfoListCut(List<ArtistRecommendInfoDto> artistRecommendInfoList, Integer width,Integer osType){
        if(CollectionUtils.isNotEmpty(artistRecommendInfoList)){
            artistRecommendInfoList.forEach(artistRecommendInfoDto -> artistRecommendInfoDto.setRecommendImage(AliImageUtil.imageCompress(artistRecommendInfoDto.getRecommendImage(),
                    osType,width, ImageConstant.SIZE_MIDDLE)));
        }
    }

    /**
     * 艺术品列表切图
     * @param artList
     * @param width
     */
    private void artListCut(List<ArtDto> artList,Integer width,Integer osType){
        if(CollectionUtils.isNotEmpty(artList)){
            artList.forEach(artDto -> artDto.setWorksPicUrl(AliImageUtil.imageCompress(artDto.getWorksPicUrl(),
                    osType,width, ImageConstant.SIZE_MIDDLE)));
        }
    }

    /**
     * 艾商城首页接口查询多线程
     * @return
     */
    private Map<String, Object> concurrentQueryArtMallList() {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return ihomeMallProxy.queryFrontCategoryList(new CategoryArtPageRequest()).getFrontCategoryInfo();
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_FRONT_CATEGORY_LIST.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return ihomeMallProxy.queryArtistRecommendList(new CategoryArtPageRequest()).getArtistRecommendInfo();
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ARTIST_RECOMMEND_LIST.name();
            }
        });

        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return ihomeMallProxy.queryArtList(new ArtListRequest()).getList();
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ART_LIST.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 艺术品列表条件检索接口
     * @param request
     * @return
     */
    @Override
    public ArtListPageResponse queryArtList(ArtListRequest request) {
        if(request==null) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        if(request.getFreeEx()!=null && request.getFreeEx() == 1){//用艾积分兑换
            java.util.Map<String, Object> param = new HashMap<>();
            if(request.getUserInfo() == null){
                throw new BusinessException(HttpResponseCode.FAILED,MessageConstant.ADMIN_ILLEGAL);
            }
            param.put("userId", request.getUserInfo().getId());
            AjbAccountDto ajbInfo = shopppingCartProxy.ajbInfo(param);
            if (ajbInfo != null) {
                BigDecimal ajbMoney = new BigDecimal(ajbInfo.getAmount()).setScale(2);
                request.setAjbMoney(ajbMoney.divide(new BigDecimal(EX_RATE)));
            } else {
                request.setAjbMoney(new BigDecimal(0));
            }
        }


        ArtListPageResponse artListPageResponse = ihomeMallProxy.queryArtList(request);

        //艺术品列表切图
        artListCut(artListPageResponse.getList(),request.getWidth(),request.getOsType());

        return artListPageResponse;
    }

    /**
     * 艺术品风格及价格筛选条件查询接口
     * @return
     */
    @Override
    public ScreenTypeResponse queryArtTypes() {
        List<PriceSectionDto> priceSectionList = new ArrayList<PriceSectionDto>();
        priceSectionList.add(new PriceSectionDto(1, "1000元以内"));
        priceSectionList.add(new PriceSectionDto(2, "1000~2000元"));
        priceSectionList.add(new PriceSectionDto(4, "2000~5000元"));
        priceSectionList.add(new PriceSectionDto(8, "5000~1万元"));
        priceSectionList.add(new PriceSectionDto(16, "1万~2万元"));
        priceSectionList.add(new PriceSectionDto(32, "2万元以上"));
        return new ScreenTypeResponse(ihomeMallProxy.queryArtTypes(new ArtListProductRequest()),priceSectionList);
    }

    /**
     * 定制专区-产品列表查询接口
     * @param request
     * @return
     */
    @Override
    public List<ProductResponse> queryProductList(ProductCategoryRequest request) {
        if(request.getArtCategoryInfo() ==null) {
            return new ArrayList<>();
        }
        List<String> collect = request.getArtCategoryInfo().stream().map(artCategoryInfoBean -> artCategoryInfoBean.getArtCategoryId()).collect(Collectors.toList());

        List<ProductResponse> productResponses = ihomeMallProxy.queryProductList(new ArtProductListIDto(collect));

        productListCut(productResponses,request.getWidth(),request.getOsType());

        return productResponses;
    }

    /**
     * 产品列表数据切图
     * @param productResponses
     * @param width
     */
    private void productListCut(List<ProductResponse> productResponses,Integer width,Integer osType){
        if(CollectionUtils.isNotEmpty(productResponses)){
            productResponses.forEach(productResponse -> {
                if(CollectionUtils.isNotEmpty( productResponse.getProductPicUrl())){
                    productResponse.setProductPicUrl(productResponse.getProductPicUrl().stream().map(a->AliImageUtil.imageCompress(a,
                            osType,width, ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()));
                }
                if(productResponse.getProductDetail()!=null){//富文本切图
                    productResponse.setProductDetail(AliImageUtil.compressdocumentBodyImage(productResponse.getProductDetail(),width));
                }

            });
        }
    }

    /**
     * 产品详情页接口
     * @param request
     * @return
     */
    @Override
    public ProductResponse queryProductInfo(ProductRequest request) {
        if(request==null || request.getProductId()==null) {
            return new ProductResponse();
        }
        Map<String, Object> stringObjectMap = concurrentqueryProductCount(request.getProductId());
        List<ProductResponse> productList = (List<ProductResponse> )stringObjectMap.get(ConcurrentTaskEnum.QUERY_PRODUCT_INFO.name());
        CustomProductPageResponse customProductPageResponse =  (CustomProductPageResponse )stringObjectMap.get(ConcurrentTaskEnum.QUERY_ART_LIST_BY_PRODUCTID.name());
        productListCut(productList,request.getWidth(),request.getOsType());
        if(CollectionUtils.isNotEmpty(productList)){
            Map map = new HashMap<>();
            map.put("productIds",Arrays.asList(request.getProductId()));
            VisitResponse visitResponse = ihomeMallProxy.queryVisitRecordByProductId(map);
            ProductResponse productResponse = productList.get(0);
            if(productResponse!=null){
                if(CollectionUtils.isNotEmpty(customProductPageResponse.getList())){
                    productResponse.setSkuCount(customProductPageResponse.getList().size());
                }
                if(visitResponse!=null && CollectionUtils.isNotEmpty(visitResponse.getVisitRecords())){
                    productResponse.setVisitNum(visitResponse.getVisitRecords().get(0).getVisitNum());
                }
            }
            addViewcount(request.getProductId(),1);
            return productResponse;
        }
        return new ProductResponse();
    }


    private Map<String, Object> concurrentqueryProductCount(String productId) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        //查询可定制商品
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                ArtListProductRequest artListProductRequest =  new ArtListProductRequest();
                artListProductRequest.setProductId(productId);
                return ihomeMallProxy.queryArtListByProductId(artListProductRequest);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ART_LIST_BY_PRODUCTID.name();
            }
        });

        //产品详情页接口
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return ihomeMallProxy.queryProductInfo(new ArtProductListIDto().setProductIdList(Arrays.asList(productId)));
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_PRODUCT_INFO.name();
            }
        });


        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 根据产品id查询相关可定制图案接口
     * @param request
     * @return
     */
    @Override
    public CustomProductPageResponse queryArtListByProductId(ArtListProductRequest request) {
        Map<String, Object> stringObjectMap = concurrentqueryArtListByProductId(request);

        CustomProductPageResponse customProductPageResponse =  (CustomProductPageResponse )stringObjectMap.get(ConcurrentTaskEnum.QUERY_ART_LIST_BY_PRODUCTID.name());

        List<StyleTypeDto> styleTypeDtos =  (List<StyleTypeDto> )stringObjectMap.get(ConcurrentTaskEnum.QUERY_ART_TYPES.name());

        if(customProductPageResponse == null){
            throw new BusinessException(HttpReturnCode.DING_MONITOR_WHITE_END, MessageConstant.CUSTOM_SKU_NOT_EXISTS);
        }
        if(CollectionUtils.isNotEmpty(customProductPageResponse.getList())){
            for (CustomProductDto customProductDto : customProductPageResponse.getList()) {
                if(CollectionUtils.isNotEmpty(customProductDto.getProductPicUrlList())){
                    customProductDto.setProductPicUrl(AliImageUtil.imageCompress(customProductDto.getProductPicUrlList().get(0).getProductPicUrl(),
                            request.getOsType(),request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    customProductDto.setWorksPicUrl(AliImageUtil.imageCompress(customProductDto.getWorksPicUrl(),
                            request.getOsType(),request.getWidth(), ImageConstant.SIZE_MIDDLE));
                    setCustomProductDtoPicUrl(customProductDto,request.getWidth(),request.getOsType());
                    customProductDto.setProductPicUrlList(null);
                }
            }
        }
        customProductPageResponse.setStyleTypeDtos(styleTypeDtos);
        return customProductPageResponse;
    }


    /**
     * 设置sku头图并切图
     * @param customProductDto
     * @param width
     */
    private void setCustomProductDtoPicUrl(CustomProductDto customProductDto,Integer width,Integer osType){
        if(StringUtils.isNotBlank(customProductDto.getPicUrl())){
           if(customProductDto.getPicUrl().indexOf(",")>-1){
               String[] split = customProductDto.getPicUrl().split(",");
               customProductDto.setPicUrl(AliImageUtil.imageCompress(split[0],
                       osType,width, ImageConstant.SIZE_MIDDLE));
           }else{
               customProductDto.setPicUrl(AliImageUtil.imageCompress(customProductDto.getPicUrl(),
                       osType,width, ImageConstant.SIZE_MIDDLE));
           }
        }

    }


    /**
     * 根据产品id查询相关可定制图案接口
     * @return
     */
    private Map<String, Object> concurrentqueryArtListByProductId(ArtListProductRequest request) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(2);

        //查询可定制商品
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return ihomeMallProxy.queryArtListByProductId(request);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ART_LIST_BY_PRODUCTID.name();
            }
        });

        //查询分类信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return ihomeMallProxy.queryArtTypes(request);
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ART_TYPES.name();
            }
        });


        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 定制商品详情页查询接口
     * @param request
     * @return
     */
    @Override
    public CustomSkuResponse queryCustomProductInfo(CustomSkuRequest request) {
        ArtProductAllSkuListDto artProductAllSkuListDto = ihomeMallProxy.queryCustomProductInfo(request);
        CustomSkuResponse customSkuResponse = new CustomSkuResponse();
        if(artProductAllSkuListDto!=null){
            customSkuResponse.setInventoryCount(artProductAllSkuListDto.getInventoryCount());
            customSkuResponse.setOnlineStatus(artProductAllSkuListDto.getOnlineStatus());
            if(CollectionUtils.isNotEmpty(artProductAllSkuListDto.getArtProductSkuODtoList())){

                Map<String, BigDecimal> stringBigDecimalMap = queryMaxAndMinPrice(artProductAllSkuListDto);
                customSkuResponse.setMinPrice(stringBigDecimalMap.get("minPrice"));
                customSkuResponse.setMaxPrice(stringBigDecimalMap.get("maxPrice"));
                List<PropertyDto> propertyList = new ArrayList<>();
                List<String> customDefaultPicList = new ArrayList<>();
                Map<String, ArtSkuDto> skuMap = new HashMap<>();
                for (ArtSkuDto artSkuDto : artProductAllSkuListDto.getArtProductSkuODtoList()) {
                    setCustomDefaultPicList(customDefaultPicList,artSkuDto.getPicUrl(),request.getWidth(),request.getOsType());
                    if(CollectionUtils.isNotEmpty(artSkuDto.getProperties())){
                        artSkuDto.setProperties(artSkuDto.getProperties().stream().filter(propertyDto -> !propertyDto.getPropertyName().equals("作品")).collect(Collectors.toList()));//过滤掉作品这个属性
                        propertyList.addAll(artSkuDto.getProperties());
                        String key="";
                        artSkuDto.getProperties().sort(Comparator.comparing(PropertyDto::getPropertySeq));
                        for (PropertyDto property : artSkuDto.getProperties()) {
                            setPicUrlList(artSkuDto.getPicUrl(),artSkuDto, request.getWidth(),request.getOsType());
                            if(StringUtils.isNotBlank(key)){
                                key = key + ";";
                            }
                            key = key + property.getPropertySeq()+":"+property.getPropertyValue();
                        }
                        if(StringUtils.isNotBlank(key)){
                            skuMap.put(key+";",artSkuDto);
                        }

                    }

                }
                customSkuResponse.setPropertyGroupList(getpropertyGroupList(propertyList.stream().distinct().collect(Collectors.toList())));
                customSkuResponse.setCustomDefaultPicList(customDefaultPicList.stream().distinct().collect(Collectors.toList()));
                customSkuResponse.setSkuList(skuMap);
            }
        }


        return customSkuResponse;
    }

    /**
     * 获取图片列表
     * @param picUrl
     * @param artSkuDto
     */
    private void setPicUrlList(String picUrl,ArtSkuDto artSkuDto,Integer width,Integer osType){
        List<String> list = new ArrayList<>();
        if(StringUtils.isNotBlank(picUrl)){
            if(picUrl.indexOf(",")>-1){
                String[] split = picUrl.split(",");
                for (String url : split) {
                    list.add( AliImageUtil.imageCompress(url, osType,  width, ImageConstant.SIZE_MIDDLE));
                }
            }else{
                list.add( AliImageUtil.imageCompress(picUrl, osType,  width, ImageConstant.SIZE_MIDDLE));
            }


        }
        artSkuDto.setPicUrlList(list);

    }

    /**
     * 切图及多图处理，多图上游以,分割
     * @param customDefaultPicList
     * @param picUrl
     * @param width
     */
    private void setCustomDefaultPicList(List<String> customDefaultPicList,String picUrl,Integer width,Integer osType){
        if(StringUtils.isNotBlank(picUrl)){
            if(picUrl.indexOf(",")>-1){
                String[] picUrlSplit = picUrl.split(",");
                for (String url : picUrlSplit) {
                    customDefaultPicList.add(AliImageUtil.imageCompress(url,osType,width, ImageConstant.SIZE_MIDDLE));
                }
            }else{
                customDefaultPicList.add(AliImageUtil.imageCompress(picUrl,osType,width, ImageConstant.SIZE_MIDDLE));
            }
        }
    }

    /**
     * 获取最低价和最高价
     * @param artProductAllSkuListDto
     * @return
     */
    private Map<String,BigDecimal> queryMaxAndMinPrice(ArtProductAllSkuListDto artProductAllSkuListDto){
        BigDecimal minPrice = BigDecimal.ZERO;
        BigDecimal maxPrice = BigDecimal.ZERO;
        if(CollectionUtils.isNotEmpty(artProductAllSkuListDto.getArtProductSkuODtoList())){
            minPrice = artProductAllSkuListDto.getArtProductSkuODtoList().stream().map(i->i.getPrice()).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
            maxPrice = artProductAllSkuListDto.getArtProductSkuODtoList().stream().map(i->i.getPrice()).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        }
        Map<String,BigDecimal> map= new HashMap<>();
        map.put("minPrice",minPrice);
        map.put("maxPrice",maxPrice);
        return map;
    }

    /**
     * 根据画作id查询画作详情
     * @param request
     * @return
     */
    @Override
    public ArtInfoResponse queryArtInfo(WorksRequest request) {
        ArtInfoResponse artInfoResponse = ihomeMallProxy.queryArtInfo(request);
        Map viewMap = new HashMap<>();
        viewMap.put("productIds",Arrays.asList(request.getWorksId()));
        VisitResponse visitResponse = ihomeMallProxy.queryVisitRecordByProductId(viewMap);
        if(visitResponse!=null && CollectionUtils.isNotEmpty(visitResponse.getVisitRecords())){
            artInfoResponse.setVisitNum(visitResponse.getVisitRecords().get(0).getVisitNum());
        }
        setWorksSize(artInfoResponse);
        artInfoCut(artInfoResponse,request.getWidth(),request.getOsType());
        if(CollectionUtils.isNotEmpty(artInfoResponse.getSpecificationList())){
            List<PropertyDto> propertyList = new ArrayList<>();
            Map<String, ArtSpecificationDto> skuMap = new HashMap<>();
            artInfoResponse.setMinWorksPrice(artInfoResponse.getSpecificationList().stream().map(i->i.getPrice()).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
            for (SpecificationExtendDto specificationExtendDto : artInfoResponse.getSpecificationList()) {
                ArtSpecificationDto artSpecificationDto = new ArtSpecificationDto();
                artSpecificationDto.setPicUrl(AliImageUtil.imageCompress(specificationExtendDto.getPicUrl(), request.getOsType(),  request.getWidth(), ImageConstant.SIZE_MIDDLE));
                artSpecificationDto.setWorksId(artInfoResponse.getWorksId());
                artSpecificationDto.setPrice(specificationExtendDto.getPrice());
                artSpecificationDto.setSpecificationId(specificationExtendDto.getSpecificationId());
                if(CollectionUtils.isNotEmpty(specificationExtendDto.getExtendList())){
                    String key="";
                    specificationExtendDto.setExtendList(specificationExtendDto.getExtendList().stream().filter(propertyDto -> !propertyDto.getPropertyName().equals("作品")).collect(Collectors.toList()));//过滤掉作品这个属性
                    propertyList.addAll(specificationExtendDto.getExtendList());
                    specificationExtendDto.getExtendList().sort(Comparator.comparing(PropertyDto::getPropertySeq));
                    for (PropertyDto propertyDto : specificationExtendDto.getExtendList()) {
                        //属性序号:属性值;
                        if(StringUtils.isNotBlank(key)){
                            key = key + ";";
                        }
                        key = key + propertyDto.getPropertySeq()+":"+propertyDto.getPropertyValue();
                    }
                    if(StringUtils.isNotBlank(key)){
                        skuMap.put(key+";",artSpecificationDto);
                    }
                }
            }
            artInfoResponse.setSkuList(skuMap);
            artInfoResponse.setPropertyGroupList(getpropertyGroupList(propertyList.stream().distinct().collect(Collectors.toList())));
        }
        artInfoResponse.setSpecificationList(null);
        addViewcount(request.getWorksId(),2);
        if(request.getAppVersion()!=null && VersionUtil.mustUpdate(request.getAppVersion(),"5.5.2")){
            artInfoResponse.setMinPrice(artInfoResponse.getMinWorksPrice());
        }
        return artInfoResponse;
    }

    /**
     * 设置画作尺寸
     * @param artInfoResponse
     */
    private void setWorksSize(ArtInfoResponse artInfoResponse) {
        if(artInfoResponse!=null){
            artInfoResponse.setWorksSize((artInfoResponse.getWorksSize()==null?"":artInfoResponse.getWorksSize())+" 厘米");
        }
    }

    /**
     * 作品或艺术品浏览量+1
     * @param goodsId
     * @param type 1产品 2艺术品
     */
    private void addViewcount(String goodsId ,Integer type){
        List<TaskAction<?>> taskActions = new ArrayList<>();
        taskActions.add(() -> {
            Map map = new HashMap<>();
            map.put("productType",type);
            map.put("productId",goodsId);
            ihomeMallProxy.addViewcount(map);
            return 1;
        });
        Executor.getInvokeOuterServiceFactory().asyncExecuteTask(taskActions);
    }

    /**
     * 属性进行分组
     * @param unGroupList
     * @return
     */
    private  List<PropertyGroupDto> getpropertyGroupList(List<PropertyDto> unGroupList){
        List<PropertyGroupDto> propertyGroupList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(unGroupList)){
            unGroupList.sort(Comparator.comparingInt(PropertyDto::getPropertySeq));
            Set<String> set= new LinkedHashSet<>();
            for (PropertyDto propertyDto : unGroupList) {
                set.add(propertyDto.getPropertyName());
            }
            for (String porpertyName : set) {
                PropertyGroupDto propertyGroupDto = new PropertyGroupDto();
                propertyGroupDto.setPropertyName(porpertyName);
                List<PropertyDto> propertyInnerList = new ArrayList<>();
                for (PropertyDto propertyDto : unGroupList) {
                    if(porpertyName.equals(propertyDto.getPropertyName())){
                        propertyInnerList.add(propertyDto);
                    }
                }
                propertyGroupDto.setPropertyList(propertyInnerList);
                propertyGroupList.add(propertyGroupDto);
            }
        }
        return propertyGroupList;
    }

    /**
     * 获取艺术品最低价和最高价
     * @param specificationList
     * @return
     */
    private Map<String,BigDecimal> queryArtMaxAndMinPrice(List<SpecificationExtendDto> specificationList){
        BigDecimal minPrice = BigDecimal.ZERO;
        BigDecimal maxPrice = BigDecimal.ZERO;
        for (int i = 0; i < specificationList.size(); i++) {
            if(i==0){
                minPrice = specificationList.get(i).getPrice();
                maxPrice = specificationList.get(i).getPrice();
            }
            if(i==specificationList.size()-1){
                break;
            }
            if(specificationList.get(i).getPrice().compareTo(maxPrice) > 0){//大于
                maxPrice=specificationList.get(i).getPrice();
            }
            if(specificationList.get(i).getPrice().compareTo(minPrice) < 0){//小于
                minPrice=specificationList.get(i).getPrice();
            }
        }
        Map<String,BigDecimal> map= new HashMap<>();
        map.put("minPrice",minPrice);
        map.put("maxPrice",maxPrice);
        return map;
    }

    /**
     * 艺术品信息切图
     * @param artInfoResponse
     * @param width
     */
    private void artInfoCut(ArtInfoResponse artInfoResponse,Integer width,Integer osType){
        artInfoResponse.getArtistInfo().setPersonPicUrl(AliImageUtil.imageCompress(artInfoResponse.getArtistInfo().getPersonPicUrl(), osType,  width, ImageConstant.SIZE_SMALL));

        artInfoResponse.setWorksPicList(Arrays.asList(AliImageUtil.imageCompress(artInfoResponse.getWorksPicUrl(), osType,  width, ImageConstant.SIZE_MIDDLE)));
//        artInfoResponse.setWorksPicList(artInfoResponse.getWorksPicList().stream().map(worksPic-> AliImageUtil.imageCompress(worksPic, 2,  width, ImageConstant.SIZE_MIDDLE))
//                .collect(Collectors.toList()));
        artInfoResponse.setIntroductionHtml(AliImageUtil.compressdocumentBodyImage(artInfoResponse.getIntroductionHtml(),width));
    }


    /**
     * 艺术家主页查询
     * @param request
     * @return
     */
    @Override
    public ArtistInfoResponse queryArtistInfo(HttpArtistRequest request) {
        List<ArtistInfoResponse> artistInfoResponseList = ihomeMallProxy.queryArtistInfo(request);
        if(CollectionUtils.isEmpty(artistInfoResponseList)){
            return new ArtistInfoResponse();
        }
        ArtistInfoResponse artistInfoResponse = artistInfoResponseList.get(0);
        artistInfoCut(artistInfoResponse,request.getWidth(),request.getOsType());
        return artistInfoResponse;
    }

    /**
     * 艺术家信息切图
     * @param artistInfoResponse
     * @param width
     */
    private void artistInfoCut(ArtistInfoResponse artistInfoResponse,Integer width,Integer osType){
        artistInfoResponse.setPersonPicUrl(AliImageUtil.imageCompress(artistInfoResponse.getPersonPicUrl(), osType,  width, ImageConstant.SIZE_SMALL));
        artistInfoResponse.setIntroductionHtml(AliImageUtil.compressdocumentBodyImage(artistInfoResponse.getIntroductionHtml(),width));
    }
}
