package com.ihomefnt.o2o.service.service.program.customgoods;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupVO;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.GroupDetailDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.GroupDetailListDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.MaterialDetailDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.dto.MaterialOptionsDto;
import com.ihomefnt.o2o.intf.domain.program.customgoods.request.*;
import com.ihomefnt.o2o.intf.domain.program.customgoods.response.*;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.QueryCabinetPropertyListResponseDolly;
import com.ihomefnt.o2o.intf.domain.program.vo.response.QueryCabinetPropertyListResponseNew;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.program.customgoods.CurtainProxy;
import com.ihomefnt.o2o.intf.service.program.ProductProgramService;
import com.ihomefnt.o2o.intf.service.program.customgoods.CurtainService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 窗帘定制服务
 *
 * @author liyonggang
 * @create 2019-03-18 19:48
 */
@Service
public class CurtainServiceImpl implements CurtainService {

    @Autowired
    private CurtainProxy curtainProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private ProductProgramService productProgramService;

    private final String BOM_FREE_REPLACE_MESSAGE="我们已为您提供几款免费赠品窗帘供选择，您也可以自行选择非赠品窗帘，但需要支付额外费用。";

    private final String SHA_PIC="https://img13.ihomefnt.com/afbe883bc0c7ac07d0036bc5d07cea87f5740a5919d59046a8e387b16b0b9985.png";

    /**
     * 根据物料id查询物料详情
     *
     * @param request
     * @return
     */
    public DetailVO queryMaterialDetail(DetailQueryRequest request) {
        MaterialDetailDto detailDto = curtainProxy.queryMaterialDetail(request);
        if (detailDto == null) {
            throw new BusinessException(MessageConstant.QUERY_FAILED);
        }
        if (detailDto.getHeight() == null && detailDto.getWidth() == null && detailDto.getLength() == null) {
            request.setGroupId(request.getDefaultGroupId());
            request.setMaterielId(request.getDefaultMaterielId());
            MaterialDetailDto defaultDetailDto = curtainProxy.queryMaterialDetail(request);
            if (defaultDetailDto != null) {
                detailDto.setHeight(defaultDetailDto.getHeight());
                detailDto.setWidth(defaultDetailDto.getWidth());
                detailDto.setLength(defaultDetailDto.getLength());
            }
        }
        DetailVO result = new DetailVO();
        result.setId(detailDto.getMaterialId());
        result.setTitle(detailDto.getMaterialName());
        result.setLastCategoryId(detailDto.getLastCategoryId());
        result.setLastCategoryName(detailDto.getLastCategoryName());
        result.setTopCategoryId(detailDto.getTopCategoryId());
        List<ImageVO> materialImageList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(detailDto.getMaterialImageList())) {
            detailDto.getMaterialImageList().forEach(materialImage -> materialImageList.add(ImageVO.buildImageVO(materialImage, request.getWidth())));
        }
        result.setHeadImages(materialImageList);
        ElementVO element = new ElementVO();
        element.setImageList(result.getHeadImages());
        element.setTitle("属性");
        if (CollectionUtils.isNotEmpty(detailDto.getOptionAttrList())) {
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMaximumFractionDigits(2);
            detailDto.getOptionAttrList().removeIf(optionAttrListBean -> optionAttrListBean!=null && optionAttrListBean.getOptionName() !=null && optionAttrListBean.getOptionName().equals("是否免费赠品")||optionAttrListBean.getOptionName().equals("是否默认赠品"));
            element.setAttributeList(detailDto.getOptionAttrList().stream().map(optionAttrListBean -> Attribute.build(optionAttrListBean.getOptionName(), optionAttrListBean.getAttrValue())).collect(Collectors.toList()));
            if (detailDto.getBrandName() != null) {
                element.getAttributeList().add(0, Attribute.build("品牌", detailDto.getBrandName()));
            }
            if (detailDto.getLength() != null || detailDto.getWidth() != null || detailDto.getHeight() != null) {
                element.getAttributeList().add(Attribute.build("尺寸", ((detailDto.getLength() == null
                        || detailDto.getLength() <= 0 ? "" : "长" + numberInstance.format(detailDto.getLength() / 1000.0) + "米")
                        + (detailDto.getWidth() == null || detailDto.getWidth() <= 0 ? "" : "，宽" + numberInstance.format(detailDto.getWidth() / 1000.0) + "米")
                        + (detailDto.getHeight() == null || detailDto.getHeight() <= 0 ? "" : "，高" + numberInstance.format(detailDto.getHeight() / 1000.0) + "米"))));
            }

        }
        result.setElementList(Lists.newArrayList(element));
        return result;
    }

    /**
     * 根据组合id查询组合详情
     *
     * @param request
     * @return
     */
    public DetailVO queryGroupDetail(DetailQueryRequest request) {
        GroupDetailDto detailDto = curtainProxy.queryGroupDetail(request);
        DetailVO result = new DetailVO();
        List<ImageVO> groupImageList = new ArrayList<>();
        if(detailDto == null){
            throw new BusinessException(HttpReturnCode.PRODUCT_WEB_FAILED, MessageConstant.FAILED);
        }
        if (CollectionUtils.isNotEmpty(detailDto.getGroupImageList())) {
            detailDto.getGroupImageList().forEach(groupImage -> groupImageList.add(ImageVO.buildImageVO(groupImage, request.getWidth())));
        }
        result.setHeadImages(groupImageList);
        result.setId(detailDto.getGroupId());
        result.setTitle(detailDto.getGroupName());
        result.setLastCategoryId(detailDto.getLastCategoryId());
        result.setLastCategoryName(detailDto.getLastCategoryName());
        if (CollectionUtils.isNotEmpty(detailDto.getComponentList())) {
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMaximumFractionDigits(2);
            result.setElementList(detailDto.getComponentList().stream().map(componentListBean -> {
                ElementVO elementVO = new ElementVO();
                elementVO.setTitle(componentListBean.getComponentCategoryName());
                if (componentListBean.getMaterialDetail() != null) {
                    elementVO.setImageList(Lists.newArrayList(ImageVO.buildImageVO(componentListBean.getMaterialDetail().getMaterialImage(), request.getWidth())));
                    if (CollectionUtils.isNotEmpty(componentListBean.getMaterialDetail().getOptionAttrList())) {
                        componentListBean.getMaterialDetail().getOptionAttrList().removeIf(optionAttrListBean -> optionAttrListBean.getOptionName().equals("是否免费赠品")||optionAttrListBean.getOptionName().equals("是否默认赠品"));
                        elementVO.setAttributeList(componentListBean.getMaterialDetail().getOptionAttrList().stream().map(optionAttrListBean -> Attribute.build(optionAttrListBean.getOptionName(), optionAttrListBean.getAttrValue())).collect(Collectors.toList()));
                        if (StringUtils.isNotBlank(componentListBean.getMaterialDetail().getBrandName())) {
                            elementVO.getAttributeList().add(0, Attribute.build("品牌", componentListBean.getMaterialDetail().getBrandName()));
                        }
                        if (componentListBean.getMaterialDetail().getLength() != null || componentListBean.getMaterialDetail().getWidth() != null || componentListBean.getMaterialDetail().getHeight() != null) {
                            String size = (componentListBean.getMaterialDetail().getLength() == null
                                    || componentListBean.getMaterialDetail().getLength() <= 0 ? ""
                                    : "长" + numberInstance.format(componentListBean.getMaterialDetail().getLength() / 1000.0) + "米")
                                    + (componentListBean.getMaterialDetail().getWidth() == null
                                    || componentListBean.getMaterialDetail().getWidth() <= 0 ? ""
                                    : "，宽" + numberInstance.format(componentListBean.getMaterialDetail().getWidth() / 1000.0) + "米")
                                    + (componentListBean.getMaterialDetail().getHeight() == null
                                    || componentListBean.getMaterialDetail().getHeight() <= 0 ? ""
                                    : "，高" + numberInstance.format(componentListBean.getMaterialDetail().getHeight() / 1000.0) + "米");
                            if (StringUtils.isNotBlank(size)) {
                                elementVO.getAttributeList().add(Attribute.build("尺寸", size));
                            }
                        }
                    }
                }
                return elementVO;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    /**
     * 根据组合分类id查询物料筛选项
     *
     * @param request
     * @return
     */
    public List<QueryMaterialOptionsVO> queryMaterialOptions(DetailQueryRequest request) {
        MaterialOptionsDto optionsDto = curtainProxy.queryMaterialOptions(request);
        if (optionsDto == null) {
            throw new BusinessException(MessageConstant.QUERY_FAILED);
        }
        List<MaterialOptionsDto.OptionsBean> options = optionsDto.getOptions();
        if (CollectionUtils.isEmpty(options)) {
            throw new BusinessException(MessageConstant.QUERY_EMPTY);
        }
        options.removeIf(optionsBean -> optionsBean.getOptionId()!=null && (optionsBean.getOptionId().equals(4)||optionsBean.getOptionId().equals(5)));
        return options.stream().map(optionsBean -> {
            QueryMaterialOptionsVO queryMaterialOptionsVO = new QueryMaterialOptionsVO();
            queryMaterialOptionsVO.setOptionId(optionsBean.getOptionId());
            queryMaterialOptionsVO.setOptionValue(optionsBean.getOptionName());
            if (CollectionUtils.isNotEmpty(optionsBean.getAttrs())) {
                queryMaterialOptionsVO.setOptions(optionsBean.getAttrs().stream().map(attrsBean -> QueryMaterialOptionsVO.OptionsBean.build(attrsBean.getAttrId(), attrsBean.getAttrValue())).collect(Collectors.toList()));
            }
            return queryMaterialOptionsVO;
        }).collect(Collectors.toList());
    }

    /**
     * 保存组合
     *
     * @param request
     * @return
     */
    @Override
    public GroupSaveVO saveGroup(GroupSaveRequest request) {
        GroupSaveVO groupSaveVO = curtainProxy.saveGroup(request);
        if (groupSaveVO != null) {
            groupSaveVO.setImages(Lists.newArrayList(ImageVO.buildImageVO(groupSaveVO.getGroupImage(), request.getWidth())));
        }
        return groupSaveVO;
    }

    /**
     * 分页查询物料信息
     *
     * @param request
     * @return
     */
    @Override
    public MaterialForPageVO querMaterialForPage(QuerMaterialForPageRequest request) {
        MaterialForPageVO materialForPageVO = curtainProxy.querMaterialForPage(request);
        if (materialForPageVO != null && CollectionUtils.isNotEmpty(materialForPageVO.getRecords())) {
            if(request.getDefaultYarnPrice()!=null && materialForPageVO.getCurrent()==1 && request.getComponentCategoryId()!=null
                    && request.getComponentCategoryId().equals(ProductProgramPraise.COMPONENT_CATEGORY_ID)){//纱组件
                MaterialForPageVO.RecordsBean recordsBean1 = new MaterialForPageVO.RecordsBean();
                if(request.getSelectedMaterialId()==null){
                    materialForPageVO.getRecords().add(0,recordsBean1);
                }else{
                    materialForPageVO.getRecords().add(1,recordsBean1);
                }
                recordsBean1.setMaterialName("不需要窗帘纱");
                recordsBean1.setMaterialImage(SHA_PIC);
                recordsBean1.setPriceDifferences(BigDecimal.ZERO.subtract(request.getDefaultYarnPrice()));
            }
            materialForPageVO.getRecords().forEach(recordsBean -> {
                if(request.getDefaultMaterialId()!=null && recordsBean.getMaterialId() !=null
                        && request.getFreeAble()!=null && request.getFreeAble()== 1&& recordsBean.getMaterialId().equals(request.getDefaultMaterialId())){
                    recordsBean.setShowFreeFlag(4);
                }
                if(recordsBean.getFreeFlag()==1){
                    recordsBean.setShowFreeFlag(2);
                }
                recordsBean.setBanners(Lists.newArrayList(ImageVO.buildImageVO(recordsBean.getMaterialImage(), request.getWidth())));
                if (recordsBean.getPriceDifferences() == null ) {
                    recordsBean.setPriceDifferences(BigDecimal.ZERO);
                }
            });
        }
        return materialForPageVO;
    }


    @Override
    public GroupReplaceDetailVO queryGroupReplaceDetail(QueryGroupReplaceDetailRequest request) {
        final boolean[] hasEmptyGroup = {false};
        GroupReplaceDetailVO groupReplaceDetailVO = curtainProxy.queryGroupReplaceDetail(request);
        if (groupReplaceDetailVO != null) {
            groupReplaceDetailVO.setDefaultGroupImageList(Lists.newArrayList(ImageVO.buildImageVO(groupReplaceDetailVO.getDefaultGroupImage(), request.getWidth())));
            groupReplaceDetailVO.setReplaceGroupImageList(Lists.newArrayList(ImageVO.buildImageVO(groupReplaceDetailVO.getReplaceGroupImage(), request.getWidth())));
            if (CollectionUtils.isNotEmpty(groupReplaceDetailVO.getComponentList())) {
                groupReplaceDetailVO.getComponentList().forEach(componentList -> {
                    GroupReplaceDetailVO.ComponentList.Material defaultMaterial = componentList.getDefaultMaterial();
                    GroupReplaceDetailVO.ComponentList.Material replaceMaterial = componentList.getReplaceMaterial();
                    if(replaceMaterial==null){
                        hasEmptyGroup[0] =true;
                    }
                    setShowFreeFlag(defaultMaterial,replaceMaterial,request.getFreeAble());
                    if (componentList.getPriceDifferences() == null) {
                        componentList.setPriceDifferences(BigDecimal.ZERO);
                    }
//                    if(replaceMaterial!=null &&replaceMaterial.getFreeFlag()!=null && replaceMaterial.getFreeFlag()==1){//选择免费赠品减少价格
//                        componentList.setPriceDifferences(getPriceDifferences(replaceMaterial.getPrice().subtract(defaultMaterial.getPrice()),componentList.getQuantities(),componentList.getComponentNum()));
//                    }

                    if (defaultMaterial != null) {
                        defaultMaterial.setMaterialImageList(Lists.newArrayList(ImageVO.buildImageVO(defaultMaterial.getMaterialImage(), request.getWidth())));
                        if(replaceMaterial!=null){
                            replaceMaterial.setMaterialImageList(Lists.newArrayList(ImageVO.buildImageVO(replaceMaterial.getMaterialImage(), request.getWidth())));
                        }
                    }
                });

                //标配沙价格”不为空&“没有沙这个组件”
                if(groupReplaceDetailVO.getDefaultYarnPrice()!=null && !hasSandComponent(groupReplaceDetailVO.getComponentList())){
                    GroupReplaceDetailVO.ComponentList componentList = new GroupReplaceDetailVO.ComponentList();
                    componentList.setPriceDifferences(BigDecimal.ZERO.subtract(groupReplaceDetailVO.getDefaultYarnPrice()));
                    componentList.setComponentId(groupReplaceDetailVO.getDefaultYarnComponentId());
                    componentList.setComponentCategoryName("窗帘纱");
                    componentList.setReplaceMaterialFlag(true);
                    GroupReplaceDetailVO.ComponentList.Material replaceMaterial = new GroupReplaceDetailVO.ComponentList.Material();
                    replaceMaterial.setMaterialImageList(Lists.newArrayList(ImageVO.buildImageVO(SHA_PIC, request.getWidth())));
                    componentList.setReplaceMaterial(replaceMaterial);
                    groupReplaceDetailVO.getComponentList().add(componentList);
                }else if(groupReplaceDetailVO.getDefaultYarnPrice()!=null && hasEmptyGroup[0]){// //兼容另外一种返回没有窗帘纱
                    GroupReplaceDetailVO.ComponentList.Material defaultMaterial = null;
                    Integer index = 0;
                    for (int i = 0; i < groupReplaceDetailVO.getComponentList().size(); i++) {
                        if(groupReplaceDetailVO.getComponentList().get(i).getComponentCategoryId().equals(ProductProgramPraise.COMPONENT_CATEGORY_ID)){
                            defaultMaterial= groupReplaceDetailVO.getComponentList().get(i).getDefaultMaterial();
                            index = i;
                        }
                    }
                    groupReplaceDetailVO.getComponentList().removeIf(componentList -> componentList.getComponentCategoryId().equals(ProductProgramPraise.COMPONENT_CATEGORY_ID));
                    GroupReplaceDetailVO.ComponentList componentList = new GroupReplaceDetailVO.ComponentList();
                    componentList.setPriceDifferences(BigDecimal.ZERO.subtract(groupReplaceDetailVO.getDefaultYarnPrice()));
                    componentList.setComponentId(groupReplaceDetailVO.getDefaultYarnComponentId());
                    componentList.setComponentCategoryId(ProductProgramPraise.COMPONENT_CATEGORY_ID);
                    componentList.setComponentCategoryName("窗帘纱");
                    componentList.setReplaceMaterialFlag(true);
                    GroupReplaceDetailVO.ComponentList.Material replaceMaterial = new GroupReplaceDetailVO.ComponentList.Material();
                    replaceMaterial.setMaterialImageList(Lists.newArrayList(ImageVO.buildImageVO(SHA_PIC, request.getWidth())));
                    if(defaultMaterial!=null){
                        replaceMaterial.setMaterialCategoryId(defaultMaterial.getMaterialCategoryId());
                    }
                    componentList.setReplaceMaterial(replaceMaterial);
                    componentList.setDefaultMaterial(defaultMaterial);
                    groupReplaceDetailVO.getComponentList().add(index,componentList);
                }
            }
            groupReplaceDetailVO.setFreeMessage(BOM_FREE_REPLACE_MESSAGE);
        }
        return groupReplaceDetailVO;
    }

    /**
     * 设置免费标签
     */
    private void setShowFreeFlag( GroupReplaceDetailVO.ComponentList.Material defaultMaterial,GroupReplaceDetailVO.ComponentList.Material replaceMaterial,Integer freeAble){
        Integer defalutFreeFlag = defaultMaterial.getFreeFlag();
        Integer replaceFreeFlag = null;
        if(replaceMaterial!=null){
             replaceFreeFlag = replaceMaterial.getFreeFlag();
        }
        if(freeAble!=null && freeAble==1){
            if(defalutFreeFlag!=null && defalutFreeFlag==1){//免费
                defaultMaterial.setShowFreeFlag(2);
            }
            if(defalutFreeFlag!=null && defalutFreeFlag==0){//非免费
                defaultMaterial.setShowFreeFlag(4);
            }
            if(replaceFreeFlag!=null && replaceFreeFlag==1){//免费
                replaceMaterial.setShowFreeFlag(2);
            }
            if(replaceFreeFlag!=null && replaceFreeFlag==0 && defaultMaterial.getMaterialId().equals(replaceMaterial.getMaterialId())){//非免费
                replaceMaterial.setShowFreeFlag(4);
            }

        }


    }

    /**
     * 获取总价差价 （单价 * 用量 向上取整）* 物料数量
     * @param simplePrice
     * @param quantities
     * @param componentNum
     * @return
     */
    private  BigDecimal getPriceDifferences(BigDecimal simplePrice,BigDecimal quantities,Integer componentNum){
         return simplePrice.multiply(quantities).multiply(new BigDecimal(componentNum)).setScale( 0, BigDecimal.ROUND_UP );
    }


    /**
     * 是否包含窗帘沙
     * @return
     */
    private boolean hasSandComponent(List<GroupReplaceDetailVO.ComponentList> componentList){
        boolean hasSand= false;
        for (GroupReplaceDetailVO.ComponentList component : componentList) {
            if(component.getComponentCategoryId().equals(ProductProgramPraise.COMPONENT_CATEGORY_ID)){
                hasSand =true;
                break;
            }
        }
        return hasSand;
    }


    /**
     * 批量查询组合替换简单信息
     *
     * @param request
     * @return
     */
    @Override
    public List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimple(QueryGroupReplaceDetailSimpleRequest request) {
        List<QueryGroupReplaceDetailSimpleVO> queryGroupReplaceDetailSimpleVOS = curtainProxy.queryGroupReplaceDetailSimple(request.getQueryList());
        if (CollectionUtils.isNotEmpty(queryGroupReplaceDetailSimpleVOS)) {
            queryGroupReplaceDetailSimpleVOS.forEach(queryGroupReplaceDetailSimpleVO -> {
                QueryGroupReplaceDetailSimpleVO.GroupSimpleInfo defaultGroup = queryGroupReplaceDetailSimpleVO.getDefaultGroup();
                QueryGroupReplaceDetailSimpleVO.GroupSimpleInfo replaceGroup = queryGroupReplaceDetailSimpleVO.getReplaceGroup();
                if (defaultGroup != null) {
                    defaultGroup.setGroupImageList(Lists.newArrayList(ImageVO.buildImageVO(defaultGroup.getGroupImage(), request.getWidth())));
                }
                if (replaceGroup != null) {
                    replaceGroup.setGroupImageList(Lists.newArrayList(ImageVO.buildImageVO(replaceGroup.getGroupImage(), request.getWidth())));
                }
            });
        }
        return queryGroupReplaceDetailSimpleVOS;
    }

    @Override
    public List<DetailVO> queryGroupDetailList(GroupDetailListRequest request) {
        if (CollectionUtils.isNotEmpty(request.getGroupIdList())) {

            List<GroupDetailListDto> paramList = Lists.newArrayList();
            for (Integer groupId : request.getGroupIdList()) {
                DetailQueryRequest detailQueryRequest = new DetailQueryRequest();
                BeanUtils.copyProperties(request, detailQueryRequest);
                detailQueryRequest.setGroupId(groupId);
                paramList.add(new GroupDetailListDto().setDetailQueryRequest(detailQueryRequest));
            }
            paramList.parallelStream().forEach(param -> param.setDetailVO(this.queryGroupDetail(param.getDetailQueryRequest())));
            return paramList.stream().map(groupDetailListDto -> groupDetailListDto.getDetailVO()).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 定制柜详情查询
     *
     * @param request
     * @return
     */
    @Override
    public List<CabinetBomGroupDetailResponse.GroupDetailByCabinetType> queryGroupListDetailByGroupListForCabinet(QueryCabinetPropertyListRequest request) {
        Map<String, List<QueryCabinetPropertyListRequest.GroupQueryRequest>> collectByCabinetType = request.getQueryList().stream().collect(Collectors.groupingBy(groupQueryRequest -> groupQueryRequest.getCabinetType()));
        List<CabinetBomGroupDetailResponse.GroupDetailByCabinetType> resultList = Lists.newArrayList();
        collectByCabinetType.forEach((cabinetType, groupDetailListRequest) -> resultList.add(new CabinetBomGroupDetailResponse.GroupDetailByCabinetType().setCabinetType(cabinetType).setCabinetTypeName(groupDetailListRequest.get(0).getCabinetTypeName())));
        resultList.parallelStream().forEach(groupDetailByCabinetType -> {
            List<QueryCabinetPropertyListRequest.GroupQueryRequest> groupQueryRequests = collectByCabinetType.get(groupDetailByCabinetType.getCabinetType());
            List<Integer> groupIdList = groupQueryRequests.stream().map(QueryCabinetPropertyListRequest.GroupQueryRequest::getGroupId).collect(Collectors.toList());
            List<BomGroupVO> list = curtainProxy.queryGroupListDetailByGroupListForCabinet(groupIdList);
            if (CollectionUtils.isNotEmpty(list)) {
                Map<Integer, BomGroupVO> collect = list.stream().collect(Collectors.toMap(mapper -> mapper.getGroupId(), mapper -> mapper));
                list = groupIdList.stream().map(groupId -> collect.get(groupId)
                ).collect(Collectors.toList());
            }
            groupDetailByCabinetType.setGroupList(list.stream().map(bomGroupVO ->
                    new CabinetBomGroupDetailResponse.GroupSimpleDetail()
                            .setGroupImage(AliImageUtil.imageCompress(bomGroupVO.getGroupImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL))
                            .setGroupName(bomGroupVO.getGroupName())
                            .setPropertyList(bomGroupVO.getComponentList().stream().map(componentList ->
                                    new CabinetBomGroupDetailResponse.GroupPropertyList()
                                            .setRemark(componentList.getMaterialDetail().getSpecifications())
                                            .setImageUrl(AliImageUtil.imageCompress(componentList.getMaterialDetail().getMaterialImage(), request.getOsType(), request.getWidth(), ImageConstant.SIZE_SMALL))
                                            .setPropertyName(componentList.getComponentCategoryName())
                                            .setPropertyValue(componentList.getMaterialDetail().getMaterialName())
                            ).collect(Collectors.toList()))
                            .setGroupId(bomGroupVO.getGroupId())
            ).collect(Collectors.toList()));
        });
        return resultList;
    }

    /**
     * 批量保存组合
     *
     * @param request
     * @return
     */
    @Override
    public BatchSaveGroupResponse batchSaveGroup(QueryCabinetPropertyListResponseNew request) {
        BatchSaveGroupRequest batchSaveGroupRequest = new BatchSaveGroupRequest();
        Map<Integer, String> indexOfType = Maps.newHashMap();
        Integer index = 0;
        for (QueryCabinetPropertyListResponseNew.CabinetDataDto cabinetDataDto : request.getCabinetDataDtoList()) {
            Map<Integer, List<QueryCabinetPropertyListResponseNew.PropertyDataDto>> collectForComponentDataList = null;
            if (CollectionUtils.isNotEmpty(cabinetDataDto.getComponentDataList())) {
                collectForComponentDataList = cabinetDataDto.getComponentDataList().stream().collect(Collectors.groupingBy(QueryCabinetPropertyListResponseNew.PropertyDataDto::getCategoryId));
            }
            List<Integer> groupIdList = cabinetDataDto.getGroupIdList();
            QueryCabinetPropertyListResponseDolly queryCabinetPropertyListResponseDolly = productProgramProxy.queryCabinetPropertyList(groupIdList);
            List<QueryCabinetPropertyListResponseDolly.GroupList> groupList = queryCabinetPropertyListResponseDolly.getGroupList();
            List<Integer> categoryIdByType = cabinetDataDto.getDataList().stream().map(propertyDataDto -> propertyDataDto.getCategoryId()).collect(Collectors.toList());
            Map<Integer, List<Integer>> mapForCategoryIdOfPid = Maps.newHashMap();
            Map<Integer, List<QueryCabinetPropertyListResponseNew.PropertyDataDto>> integerListMap = cabinetDataDto.getDataList().stream().collect(Collectors.groupingBy(QueryCabinetPropertyListResponseNew.PropertyDataDto::getCategoryId));
            integerListMap.forEach((integer, propertyDataDtos) -> {
                List<Integer> collect = propertyDataDtos.stream().map(propertyDataDto -> propertyDataDto.getPropertyId()).collect(Collectors.toList());
                mapForCategoryIdOfPid.put(integer, collect);
            });
            for (QueryCabinetPropertyListResponseDolly.GroupList list : groupList) {
                BatchSaveGroupRequest.AppGroupSaveParamList appGroupSaveParamList = new BatchSaveGroupRequest.AppGroupSaveParamList();
                for (QueryCabinetPropertyListResponseNew.GroupRelationDto groupRelationDto : cabinetDataDto.getGroupRelationDtoList()) {
                    if (list.getId().equals(groupRelationDto.getGroupId())) {
                        appGroupSaveParamList.setDefaultGroupNum(groupRelationDto.getDefaultGroupNum());
                        appGroupSaveParamList.setDefaultGroupId(groupRelationDto.getDefaultGroupId());
                    }
                }
                for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList componentList : list.getComponentList()) {
                    BatchSaveGroupRequest.AppGroupSaveParamList.MaterialReplaceList materialReplaceList = new BatchSaveGroupRequest.AppGroupSaveParamList.MaterialReplaceList();
                    for (QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto : cabinetDataDto.getDataList()) {
                        materialReplaceList.setComponentId(componentList.getComponentId());
                        if (collectForComponentDataList == null || collectForComponentDataList.get(componentList.getCategoryId()) == null) {
                            materialReplaceList.setReplaceMaterialId(componentList.getMaterialId());
                        } else {
                            List<QueryCabinetPropertyListResponseNew.PropertyDataDto> propertyDataDtoList = collectForComponentDataList.get(componentList.getCategoryId());
                            if (CollectionUtils.isNotEmpty(propertyDataDtoList)) {
                                QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto1 = propertyDataDtoList.get(0);
                                materialReplaceList.setReplaceMaterialId(propertyDataDto1.getSelectPropertyValueCode());
                            }
                        }
                        if (categoryIdByType.contains(componentList.getCategoryId())) {
                            if (componentList.getCategoryId().equals(propertyDataDto.getCategoryId())) {
                                List<Integer> hasProperty = mapForCategoryIdOfPid.get(propertyDataDto.getCategoryId());
                                if (CollectionUtils.isNotEmpty(hasProperty)) {
                                    //同一部位
                                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : componentList.getPropertyList()) {
                                        BatchSaveGroupRequest.AppGroupSaveParamList.MaterialReplaceList.PropertyValueList propertyValueList = new BatchSaveGroupRequest.AppGroupSaveParamList.MaterialReplaceList.PropertyValueList();
                                        if (hasProperty.contains(propertyList.getPropertyId())) {
                                            if (propertyList.getPropertyId().equals(propertyDataDto.getPropertyId())) {
                                                List<Integer> valueCodeListByProperty = propertyList.getTemplatePropertyValueList().stream().map(templatePropertyValueList -> templatePropertyValueList.getPropertyValueCode()).collect(Collectors.toList());
                                                if (valueCodeListByProperty.contains(propertyDataDto.getSelectPropertyValueCode())) {
                                                    //一键替换
                                                    propertyValueList.setPropertyName(propertyDataDto.getPropertyName());
                                                    propertyValueList.setPropertyId(propertyDataDto.getPropertyId());
                                                    propertyValueList.setPropertyValueCode(propertyDataDto.getSelectPropertyValueCode());
                                                } else {
                                                    //保持原样
                                                    propertyValueList.setPropertyName(propertyList.getPropertyName());
                                                    propertyValueList.setPropertyId(propertyList.getPropertyId());
                                                    propertyValueList.setPropertyValueCode(propertyList.getPropertyValueCode());
                                                }
                                            }else {
                                                //保持原样
                                                propertyValueList.setPropertyName(propertyList.getPropertyName());
                                                propertyValueList.setPropertyId(propertyList.getPropertyId());
                                                propertyValueList.setPropertyValueCode(propertyList.getPropertyValueCode());
                                            }
                                        } else {
                                            //保持原样
                                            propertyValueList.setPropertyName(propertyList.getPropertyName());
                                            propertyValueList.setPropertyId(propertyList.getPropertyId());
                                            propertyValueList.setPropertyValueCode(propertyList.getPropertyValueCode());
                                        }
                                        materialReplaceList.getPropertyValueList().add(propertyValueList);
                                    }
                                } else {
                                    for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : componentList.getPropertyList()) {
                                        //保持原样
                                        BatchSaveGroupRequest.AppGroupSaveParamList.MaterialReplaceList.PropertyValueList propertyValueList = new BatchSaveGroupRequest.AppGroupSaveParamList.MaterialReplaceList.PropertyValueList();
                                        propertyValueList.setPropertyValueCode(propertyList.getPropertyValueCode());
                                        propertyValueList.setPropertyName(propertyList.getPropertyName());
                                        propertyValueList.setPropertyId(propertyList.getPropertyId());
                                        materialReplaceList.getPropertyValueList().add(propertyValueList);
                                    }
                                }
                            }
                        } else {
                            //不包含组件，保持原样
                            for (QueryCabinetPropertyListResponseDolly.GroupList.ComponentList.PropertyList propertyList : componentList.getPropertyList()) {
                                BatchSaveGroupRequest.AppGroupSaveParamList.MaterialReplaceList.PropertyValueList propertyValueList = new BatchSaveGroupRequest.AppGroupSaveParamList.MaterialReplaceList.PropertyValueList();
                                propertyValueList.setPropertyName(propertyList.getPropertyName());
                                propertyValueList.setPropertyId(propertyList.getPropertyId());
                                propertyValueList.setPropertyValueCode(propertyList.getPropertyValueCode());
                                materialReplaceList.getPropertyValueList().add(propertyValueList);
                            }
                        }
                    }
                    appGroupSaveParamList.getMaterialReplaceList().add(materialReplaceList);
                }
                appGroupSaveParamList.setBatchIndex(index);
                indexOfType.put(index, cabinetDataDto.getCabinetType());
                batchSaveGroupRequest.getAppGroupSaveParamList().add(appGroupSaveParamList);
                index++;

            }
        }
        BatchSaveGroupResponse batchSaveGroupResponse = curtainProxy.batchSaveGroup(batchSaveGroupRequest);
        if (batchSaveGroupResponse != null && CollectionUtils.isNotEmpty(batchSaveGroupResponse.getAppGroupSimpleInfoList())) {
            for (BatchSaveGroupResponse.AppGroupSimpleInfoList appGroupSimpleInfoList : batchSaveGroupResponse.getAppGroupSimpleInfoList()) {
                appGroupSimpleInfoList.setCabinetType(indexOfType.get(appGroupSimpleInfoList.getBatchIndex()));
                appGroupSimpleInfoList.setGroupImage(AliImageUtil.imageCompress(appGroupSimpleInfoList.getGroupImage(), 1, 750, ImageConstant.SIZE_SMALL));
            }
            batchSaveGroupResponse.setTotalPriceDifferences(batchSaveGroupResponse.getAppGroupSimpleInfoList().stream().map(BatchSaveGroupResponse.AppGroupSimpleInfoList::getPriceDifferences).reduce(BigDecimal.ZERO, BigDecimal::add));

            Map<Integer, Map<String, String>> colourAndTextureByGroupIdList = productProgramService.getColourAndTextureByGroupIdList(Lists.newArrayList(batchSaveGroupResponse.getAppGroupSimpleInfoList().get(0).getGroupId()));
            if (MapUtils.isNotEmpty(colourAndTextureByGroupIdList)) {
                Map<String, String> map = colourAndTextureByGroupIdList.get(batchSaveGroupResponse.getAppGroupSimpleInfoList().get(0).getGroupId());
                if (MapUtils.isNotEmpty(colourAndTextureByGroupIdList)) {
                    batchSaveGroupResponse.setColour(map.get("colour"));
                    batchSaveGroupResponse.setTexture(map.get("texture"));
                }
            }
        }

        return batchSaveGroupResponse;
    }
}