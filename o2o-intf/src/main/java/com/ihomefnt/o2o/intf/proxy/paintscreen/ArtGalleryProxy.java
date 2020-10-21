package com.ihomefnt.o2o.intf.proxy.paintscreen;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ArtOrderRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.CommonPageRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ScreenQueryRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtHomeResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtOrderVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtistVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.BasePageVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleDetailResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.SelectedScreenResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.SelectedScreenSimpleResponse;

/**
 * @author liyonggang
 * @create 2018-12-04 14:31
 */
public interface ArtGalleryProxy {

    /**
     * 首页内容信息
     * @return
     */
    List<ArtHomeResponse> queryArtHomePage();

    /**
     * 热门作品列表
     * @param request
     * @return
     */
    BasePageVo<ScreenSimpleResponse> queryHostScreenSimple(CommonPageRequest request);


    /**
     * 精选画集列表
     * @param request
     * @return
     */
    BasePageVo<SelectedScreenResponse> querySelectedScreenSimple(ScreenQueryRequest request);


    /**
     * 画集详情
     * @param request
     * @return
     */
    BasePageVo<ScreenSimpleResponse> querySelectedScreenSimpleDetail(CommonPageRequest request);



    /**
     * 创建订单
     * @param request
     * @return
     */
    ArtOrderVo createArtOrder(ArtOrderRequest request);

    /**
     * 订单查询
     * @param userId
     * @return
     */
    ArtOrderVo queryOrderDetail(Integer userId);


    /**
     * 画作详情信息
     * @param map
     * @return
     */
    ScreenSimpleDetailResponse screenSimpleResponse(Map<String, Object> map);

    /**
     * 查询购买需知
     * @return
     */
    String getBuyInfo();

    /**
     * 根据ID查group
     * @param resourceId
     * @return
     */
    List<SelectedScreenSimpleResponse> getGroupById(Object resourceId);

    /**
     * 查看作者
     * @return
     */
    List<ArtistVo> queryArtistList();

    boolean getIsBuyOrNot(Map<String,Object> map);

    /**
     * 猜你喜欢
     * @param artId
     * @return
     */
    List<ScreenSimpleResponse> queryGuessScreenSimpleList(Object artId);

    /**
     * 获取浏览次数
     * @param map
     * @return
     */
    Integer queryBrowseCount(Map<String, Object> map);
}
