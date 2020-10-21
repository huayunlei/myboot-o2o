package com.ihomefnt.o2o.service.service.paintscreen;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ArtOrderRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.CommonPageRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ScreenQueryRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtHomePageResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtHomeResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtOrderVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.BasePageVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.BrowseCountResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleDetailResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.SelectedScreenResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.SelectedScreenSimpleResponse;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.paintscreen.ArtGalleryProxy;
import com.ihomefnt.o2o.intf.proxy.paintscreen.UserScreenProxy;
import com.ihomefnt.o2o.intf.service.paintscreen.ArtGalleryService;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 艺术画廊服务
 *
 * @author liyonggang
 * @create 2018-12-04 14:11
 */
@Service
public class ArtGalleryServiceImpl implements ArtGalleryService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ArtGalleryServiceImpl.class);

    @Autowired
    private ArtGalleryProxy artGalleryProxy;

    @Autowired
    private UserScreenProxy userScreenProxy;
    /**
     * 首页内容信息
     * @return
     */
    @Override
    public ArtHomePageResponse queryArtHomePage(Integer width) {
        List<ArtHomeResponse> list=artGalleryProxy.queryArtHomePage();
        if(CollectionUtils.isNotEmpty(list)){
            List<JSONObject> artList ;
            List<Integer> collect= new ArrayList <Integer>();
            for (ArtHomeResponse artHomeResponse:list) {
                artList=artHomeResponse.getResourceDetailList();
                if(artHomeResponse.getResourceType()!=null&&artHomeResponse.getResourceType()==0){//画作
                    if(CollectionUtils.isNotEmpty(artList)){
                        artList.parallelStream().forEach(screenSimpleResponse -> {
                            collect.add(screenSimpleResponse.getInteger("artImage"));
                        });
                    }
                }
            }
            Map<Integer, String> urls = userScreenProxy.getUrls(collect,width,2);
            for (ArtHomeResponse artHomeResponse:list) {
                 artList=artHomeResponse.getResourceDetailList();
                 if(CollectionUtils.isEmpty(artList)){
                     continue;
                 }
                 if(artHomeResponse.getResourceType()!=null&&artHomeResponse.getResourceType()==0){//画作
                     if (MapUtils.isNotEmpty(urls)) {
                         artList.parallelStream().forEach(screenSimpleResponse -> screenSimpleResponse.put("artImage",urls.get(screenSimpleResponse.getInteger("artImage"))));
                     }
                 }else if(artHomeResponse.getResourceType()!=null&&artHomeResponse.getResourceType()==1){//画集
                     Iterator <JSONObject> it = artList.iterator();
                     while (it.hasNext()){
                         JSONObject jsonobject = it.next();
                         if(jsonobject.get("artTotal")==null||(Integer)(jsonobject.get("artTotal"))==0){
                                it.remove();
                         }
                     }
                         artList.parallelStream().forEach(screenSimpleResponse -> {
                             screenSimpleResponse.put("groupImage", QiniuImageUtils.compressImageAndSamePicTwo(screenSimpleResponse.getString("groupImage"), width, -1));
                             screenSimpleResponse.put("groupTagView",getArtTagView(null,screenSimpleResponse.getString("groupCategoryStr"),screenSimpleResponse.getString("groupTag")));
                             screenSimpleResponse.put("groupAuthor", screenSimpleResponse.get("groupAuthorStr"));
                         });
//                     }
                 }
            }
        }
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return getArtHomePageResponse(list);
    }

    /**
     * list数据提取，优化前端渲染速度
     * @param list
     * @return
     */
    private ArtHomePageResponse getArtHomePageResponse(List<ArtHomeResponse> list) {
        ArtHomePageResponse artHomePageResponse = new ArtHomePageResponse();
        for(ArtHomeResponse artHomeResponse:list){
            if(artHomeResponse.getModelType()==1){
                artHomePageResponse.setTopRecommend(artHomeResponse);
            }else if(artHomeResponse.getModelType()==2){
                artHomePageResponse.setBanner(artHomeResponse);
            }else if(artHomeResponse.getModelType()==3){
                artHomePageResponse.setHostScreenSimple(artHomeResponse);
            }else if(artHomeResponse.getModelType()==4){
                artHomePageResponse.setSelectedScreenSimple(artHomeResponse);
            }else if(artHomeResponse.getModelType()==5){
                artHomePageResponse.setGuessScreenSimple(artHomeResponse);
            }

        }
        return artHomePageResponse;
    }


    /**
     * 热门作品列表
     * @param request
     * @return
     */
    @Override
    public PageResponse<ScreenSimpleDetailResponse> queryHostScreenSimple(CommonPageRequest request) {
        BasePageVo<ScreenSimpleResponse> pageList=artGalleryProxy.queryHostScreenSimple(request);
        if (pageList == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(pageList.getRows())) {
            return new PageResponse<>(Collections.emptyList(), pageList.getCurrent(), request.getPageSize(), pageList.getTotalCount(), pageList.getPages());
        }
        List<ScreenSimpleDetailResponse> list = JsonUtils.json2list(JsonUtils.obj2json(pageList.getRows()), ScreenSimpleDetailResponse.class);

        List<Integer> collect = list.stream().map(screenSimpleResponse -> screenSimpleResponse.getResourceDetail().getArtImage()==null?null:Integer.parseInt(screenSimpleResponse.getResourceDetail().getArtImage())).distinct().collect(Collectors.toList());
        Map<Integer, String> urls = userScreenProxy.getUrls(collect,request.getWidth(),2);
        if (MapUtils.isNotEmpty(urls)) {
            list.forEach(screenSimpleResponse -> screenSimpleResponse.getResourceDetail().setArtImage(urls.get(Integer.parseInt(screenSimpleResponse.getResourceDetail().getArtImage()))));
        }
        List<ScreenSimpleDetailResponse> listre = list.stream().map(a ->a).collect(Collectors.toList());

        return  new PageResponse<>(listre, pageList.getCurrent(), request.getPageSize(), pageList.getTotalCount(), pageList.getPages());
    }

    /**
     * 精选画集列表
     * @param request
     * @return
     */
    @Override
    public PageResponse <SelectedScreenResponse> querySelectedScreenSimple(ScreenQueryRequest request) {
        BasePageVo <SelectedScreenResponse> pageList= artGalleryProxy.querySelectedScreenSimple(request);
        if (pageList == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(pageList.getRows())) {
            return new PageResponse<>(Collections.emptyList(), pageList.getCurrent(), request.getPageSize(), pageList.getTotalCount(), pageList.getPages());
        }
        List<SelectedScreenResponse> list = JsonUtils.json2list(JsonUtils.obj2json(pageList.getRows()), SelectedScreenResponse.class);
//        List<Integer> collect = list.stream().map(screenSimpleResponse -> screenSimpleResponse.getResourceDetail().getGroupImage()==null?null:Integer.parseInt(screenSimpleResponse.getResourceDetail().getGroupImage())).distinct().collect(Collectors.toList());
//        Map<Integer, String> urls = userScreenProxy.getUrls(collect,request.getWidth());
//        if (MapUtils.isNotEmpty(urls)) {
            list.forEach(screenSimpleResponse -> {
                screenSimpleResponse.getResourceDetail().setGroupImage( QiniuImageUtils.compressImageAndSamePicTwo(screenSimpleResponse.getResourceDetail().getGroupImage(), request.getWidth(), -1));
                screenSimpleResponse.getResourceDetail().setGroupTagView(getArtTagView(null,screenSimpleResponse.getResourceDetail().getGroupCategoryStr(),screenSimpleResponse.getResourceDetail().getGroupTag()));
                screenSimpleResponse.getResourceDetail().setGroupAuthor(screenSimpleResponse.getResourceDetail().getGroupAuthorStr());
            });
//        }
        return  new PageResponse<>(list, pageList.getCurrent(), request.getPageSize(), pageList.getTotalCount(), pageList.getPages());
    }

    /**
     * 画集详情
     * @param request
     * @return
     */
    @Override
    public PageResponse <ScreenSimpleResponse> querySelectedScreenSimpleDetail(CommonPageRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("resourceId",request.getId());
        map.put("resourceType",1);
        addBrowse(map);//增加浏览次数
        BasePageVo <ScreenSimpleResponse> pageVoList=artGalleryProxy.querySelectedScreenSimpleDetail(request);
        if (pageVoList == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(pageVoList.getRows())) {
            return new PageResponse<>(Collections.emptyList(), pageVoList.getCurrent(),request.getPageSize(), pageVoList.getTotalCount(), pageVoList.getPages());
        }
        List<ScreenSimpleResponse> list = JsonUtils.json2list(JsonUtils.obj2json(pageVoList.getRows()), ScreenSimpleResponse.class);
        List<Integer> collect = list.stream().map(screenSimpleResponse -> screenSimpleResponse.getArtImage()==null?null:Integer.parseInt(screenSimpleResponse.getArtImage())).distinct().collect(Collectors.toList());
        Map<Integer, String> urls = userScreenProxy.getUrls(collect,request.getWidth(),2);
        if (MapUtils.isNotEmpty(urls)) {
            list.forEach(screenSimpleResponse -> screenSimpleResponse.setArtImage(urls.get(Integer.parseInt(screenSimpleResponse.getArtImage()))));
        }
        return new PageResponse<>(list, pageVoList.getCurrent(), request.getPageSize(), pageVoList.getTotalCount(), pageVoList.getPages());
    }

    /**
     * 画作详情
     * @param map
     * @return
     */
    @Override
    public ScreenSimpleDetailResponse queryScreenSimple(Map<String,Object> map) {
        ScreenSimpleDetailResponse screenSimpleDetailResponse=artGalleryProxy.screenSimpleResponse(map);
        if(screenSimpleDetailResponse==null){
            return null;
        }
        ScreenSimpleResponse resourceDetail = screenSimpleDetailResponse.getResourceDetail();
        if(resourceDetail == null) {
            throw new BusinessException(MessageConstant.QUERY_EMPTY);
        }
        if(resourceDetail.getDeleteFlag()==1){//已删除
            resourceDetail.setSellState(2);
        }

        if(map.get("userId")!=null){
            //查询是否已经购买
            map.put("artId",map.get("resourceId"));
            boolean isBuy=artGalleryProxy.getIsBuyOrNot(map);
            screenSimpleDetailResponse.setBuy(isBuy);
        }
        Map<String, Object> concurrentMap = concurrentQueryList(map);

        //查一下购买需知
        String buyInfo = (String)concurrentMap.get(ConcurrentTaskEnum.QUERY_BUY_INFO.name());
        buyInfo=buyInfo.replace("{dateRange}",getDateRange(resourceDetail.getArtDeadline()));

        //查一下画集信息
        List<SelectedScreenSimpleResponse> grouplist = (List<SelectedScreenSimpleResponse>) concurrentMap.get(ConcurrentTaskEnum.QUERY_GROUP_BYID.name());

        //猜你喜欢查询接口
        List<ScreenSimpleResponse> list= (List<ScreenSimpleResponse>) concurrentMap.get(ConcurrentTaskEnum.QUERY_GUESSSCREEN_SIMPLE.name());

        List<Integer> collect = new ArrayList <Integer>();
        if(CollectionUtils.isNotEmpty(list)){
            collect = list.stream().map(a -> a.getArtImage()==null?null:Integer.parseInt(a.getArtImage())).distinct().collect(Collectors.toList());
        }
        collect.add(resourceDetail.getArtImage()==null?null:Integer.parseInt(resourceDetail.getArtImage()));
        Map<Integer, String> urls = userScreenProxy.getUrls(collect,(Integer) map.get("width"),2);
        if (MapUtils.isNotEmpty(urls)) {
            if (CollectionUtils.isNotEmpty(list)&&MapUtils.isNotEmpty(urls)) {
                list.forEach(a -> a.setArtImage(urls.get(Integer.parseInt(a.getArtImage()))));
            }
            resourceDetail.setArtImage(urls.get(Integer.parseInt(resourceDetail.getArtImage())));//切图
            if(CollectionUtils.isNotEmpty(grouplist)){
                SelectedScreenSimpleResponse selectedScreenSimpleResponse=grouplist.get(0);
                selectedScreenSimpleResponse.setGroupAuthor(selectedScreenSimpleResponse.getGroupAuthorStr());
                selectedScreenSimpleResponse.setGroupImage(QiniuImageUtils.compressImageAndSamePicTwo(grouplist.get(0).getGroupImage(),  (Integer)map.get("width"), -1));
                selectedScreenSimpleResponse.setGroupTagView(getArtTagView(null,selectedScreenSimpleResponse.getGroupCategoryStr(),selectedScreenSimpleResponse.getGroupTag()));
                screenSimpleDetailResponse.setSelectedScreenSimpleResponse(selectedScreenSimpleResponse);
            }
        }
        resourceDetail.setArtTagView(getArtTagView(resourceDetail.getArtTime(),resourceDetail.getCategoryName(),resourceDetail.getArtTag()));
        resourceDetail.setBuyInfo(buyInfo);
        screenSimpleDetailResponse.setGuessScreenSimpleList(list);
        screenSimpleDetailResponse.setResourceDetail(resourceDetail);
        return screenSimpleDetailResponse;
    }

    /**
     * 增加浏览次数
     * @param params
     * @return
     */
    private void addBrowse(Map<String,Object> params) {

        TaskAction taskAction = new TaskAction() {
            @Override
            public Object doInAction() throws Exception {
                artGalleryProxy.screenSimpleResponse(params);
                return 1;
            }
        };
        Executor.getServiceConcurrentQueryFactory().asyncExecuteTask(taskAction);
    }

    /**
     * 查询购买需知、所属画集、猜你喜欢
     * @param params
     * @return
     */
    private Map<String, Object> concurrentQueryList(Map<String,Object> params) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        //查询购买需知
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return artGalleryProxy.getBuyInfo();
            }
            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_BUY_INFO.name();
            }
        });

        // 所属画集
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return artGalleryProxy.getGroupById(params.get("resourceId"));
            }
            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_GROUP_BYID.name();
            }
        });

        // 猜你喜欢
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() throws Exception {
                return artGalleryProxy.queryGuessScreenSimpleList(params.get("resourceId"));
            }
            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_GUESSSCREEN_SIMPLE.name();
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }

    /**
     * 创建订单
     * @param request
     * @return
     */
    @Override
    public ArtOrderVo createArtOrder(ArtOrderRequest request) {
        return artGalleryProxy.createArtOrder(request);
    }

    /**
     * 订单查询
     * @param userId
     * @return
     */
    @Override
    public ArtOrderVo queryOrderDetail(Integer userId) {
        return artGalleryProxy.queryOrderDetail(userId);
    }

    @Override
    public BrowseCountResponse queryBrowseCount(CommonPageRequest request) {
        Map<String,Object> map = new HashMap <String,Object>();
        map.put("resourceType",1);
        map.put("ids",request.getId());
        Integer count = artGalleryProxy.queryBrowseCount(map);
        BrowseCountResponse browseCountResponse = new BrowseCountResponse();
        browseCountResponse.setCount(count);
        return browseCountResponse;
    }

    /**
     * 艺术品显示标签
     * @param artTime
     * @param categoryName
     * @param artTag
     * @return
     */
    public String getArtTagView(Integer artTime, String categoryName, String artTag){
        StringBuilder artTagView=new StringBuilder();
        String year=getYear(artTime);
        if(!"".equals(year)){
            artTagView.append(year);
        }
        if(StringUtils.isNotBlank(categoryName)){
            if(!"".equals(year)){
                artTagView.append(" / ");
            }
            artTagView.append(categoryName);
        }
        if(StringUtils.isNotBlank(artTag)){
            if(StringUtils.isNotBlank(artTagView.toString())&&artTagView.lastIndexOf("/")!=artTagView.length()-1){
                artTagView.append(" / ");
            }
            artTag=artTag.replace("/"," / ");
            artTagView.append(artTag);
        }
        return artTagView.toString();
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 有效期转译
     * @param artDeadline
     * @return
     */
    private String getDateRange(Integer artDeadline){
        String dateRange="";
        switch (artDeadline){// 0:永久有效 1:一个月 2:三个月 3:半年 4:一年
            case 0:
                dateRange="永久";
                break;
            case 1:
                dateRange="一个月";
                break;
            case 2:
                dateRange="三个月";
                break;
            case 3:
                dateRange="半年";
                break;
            case 4:
                dateRange="一年";
                break;
        }
        return dateRange;
    }


    /**
     * 年代枚举值
     * @param year
     * @return
     */
    private static String getYear(Integer year){
        String dateRange="";
        if(year==null){
            return dateRange;
        }
        switch (year){// 0:古代 1:近现代 2:当代
            case 0:
                dateRange="古代";
                break;
            case 1:
                dateRange="近现代";
                break;
            case 2:
                dateRange="当代";
                break;
        }
        return dateRange;
    }

}
