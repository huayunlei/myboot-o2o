package com.ihomefnt.o2o.service.proxy.paintscreen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ArtOrderRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.CommonPageRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ScreenQueryRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtHomeResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtOrderVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtistVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.BasePageVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.BrowseCountResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleDetailResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.SelectedScreenResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.SelectedScreenSimpleResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.ArtsCentreServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.paintscreen.ArtGalleryProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 艺术画廊
 *
 * @author liyonggang
 * @create 2018-12-04 14:31
 */
@Service
public class ArtGalleryProxyImpl implements ArtGalleryProxy {

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    /**
     * 首页内容信息
     * @return
     */
    @Override
    public List<ArtHomeResponse> queryArtHomePage() {
        Map<String, Object> params = new HashMap<String, Object>();
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.GALLERY_TEMPLATE_QUERY,params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        List<ArtHomeResponse> result = JsonUtils.json2list(JSON.toJSONString(responseVo.getData(), SerializerFeature.WriteMapNullValue), ArtHomeResponse.class);
        return result;
    }

    /**
     * 热门作品列表
     * @param request
     * @return
     */
    @Override
    public BasePageVo<ScreenSimpleResponse> queryHostScreenSimple(CommonPageRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("current",request.getPageNo());
        params.put("resourceType",0);
        params.put("size",request.getPageSize());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.BROWSE_PAGE, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        BasePageVo<ScreenSimpleResponse> result = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), BasePageVo.class);
        return result;
    }

    /**
     * 精选画集列表
     * @param request
     * @return
     */
    @Override
    public BasePageVo <SelectedScreenResponse> querySelectedScreenSimple(ScreenQueryRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("current",request.getPageNo());
        params.put("resourceType",1);
        params.put("size",request.getPageSize());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.BROWSE_PAGE, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        BasePageVo <SelectedScreenResponse> result = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), BasePageVo.class);
        return result;
    }

    /**
     * 画集详情
     * @param request
     * @return
     */
    @Override
    public BasePageVo <ScreenSimpleResponse> querySelectedScreenSimpleDetail(CommonPageRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("current",request.getPageNo());
        params.put("groupId",request.getId());
        params.put("size",request.getPageSize());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.ART_GROUP_QUERY_ART_PAGE,params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        BasePageVo <ScreenSimpleResponse> result = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), BasePageVo.class);
        return result;
    }

    /**
     * 创建订单
     * @param request
     * @return
     */
    @Override
    public ArtOrderVo createArtOrder(ArtOrderRequest request) {
        ResponseVo<ArtOrderVo> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(ArtsCentreServiceNameConstants.ART_ORDER_CREATE,request, 
                    		new TypeReference<ResponseVo<ArtOrderVo>>() {
							});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return responseVo.getData();
    }

    /**
     * 订单详情
     * @param userId
     * @return
     */
    @Override
    public ArtOrderVo queryOrderDetail(Integer userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId",userId);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.ART_ORDER_QUERY_ORDER_DETAIL, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }

        ArtOrderVo result =  JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), ArtOrderVo.class);
        return result;
    }

    /**
     * 画作详情
     * @param map
     * @return
     */
    @Override
    public ScreenSimpleDetailResponse screenSimpleResponse(Map <String, Object> map) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.ARTS_CENTRE_BROWSE, map, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        ScreenSimpleDetailResponse result =  JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), ScreenSimpleDetailResponse.class);
        return result;
    }

    /**
     * 查询购买需知
     * @return
     */
    @Override
    public String getBuyInfo() {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.get(ArtsCentreServiceNameConstants.ART_QUERY_BUY_INFO,null, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return  (String)responseVo.getData();
    }

    /**
     * 根据画作ID查画集
     * @param resourceId
     * @return
     */
    @Override
    public List <SelectedScreenSimpleResponse> getGroupById(Object resourceId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("artId",resourceId);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller.get(ArtsCentreServiceNameConstants.ART_GET_GROUP, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        List<SelectedScreenSimpleResponse> result = JsonUtils.json2list(JSON.toJSONString(responseVo.getData(), SerializerFeature.WriteMapNullValue), SelectedScreenSimpleResponse.class);
        return result;
    }

    /**
     * 查询画作列表
     * @return
     */
    @Override
    public List <ArtistVo> queryArtistList() {
        ResponseVo<List<ArtistVo>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(ArtsCentreServiceNameConstants.ARTIST_QUERY_LIST, null, 
                    		new TypeReference<ResponseVo<List<ArtistVo>>>() {
							});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return  responseVo.getData();
    }

    /**
     * 查询是否购买
     * @param map
     * @return
     */
    @Override
    public boolean getIsBuyOrNot(Map<String,Object> map) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.ART_CHECK_BUY, map, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return (Boolean) responseVo.getData();
    }

    /**
     * 猜你喜欢列表
     * @param artId
     * @return
     */
    @Override
    public List <ScreenSimpleResponse> queryGuessScreenSimpleList(Object artId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("artId",artId);
        ResponseVo<List<ScreenSimpleResponse>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(ArtsCentreServiceNameConstants.ART_GUESS_LIKE, params, 
                    		new TypeReference<ResponseVo<List<ScreenSimpleResponse>>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return responseVo.getData();
    }


    /**
     * 浏览次数
     * @param map
     * @return
     */
    @Override
    public Integer queryBrowseCount(Map<String, Object> map) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.ARTS_CENTRE_TOTAL, map, ResponseVo.class);
        } catch (Exception e) {
            return 0;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return 0;
        }
        List<BrowseCountResponse> list = JsonUtils.json2list(JSON.toJSONString(responseVo.getData()), BrowseCountResponse.class);
        if(CollectionUtils.isNotEmpty(list)){
            return  list.get(0).getCount();
        }
        return 0;
    }

}
