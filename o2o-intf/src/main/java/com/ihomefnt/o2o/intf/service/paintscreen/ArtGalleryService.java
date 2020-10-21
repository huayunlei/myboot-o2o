package com.ihomefnt.o2o.intf.service.paintscreen;

import java.util.Map;

import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ArtOrderRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.CommonPageRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ScreenQueryRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtHomePageResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ArtOrderVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.BrowseCountResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleDetailResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.SelectedScreenResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

/**
 * 艺术画廊服务
 *
 * @author liyonggang
 * @create 2018-12-04 14:11
 */
public interface ArtGalleryService {
    /**
     *艺术画廊首页
     * @return
     */
    ArtHomePageResponse queryArtHomePage(Integer width);

    /**
     * 热门作品列表查询
     * @param request
     * @return
     */
    PageResponse<ScreenSimpleDetailResponse> queryHostScreenSimple(CommonPageRequest request);

    /**
     * 精选画集列表查询
     * @param request
     * @return
     */
    PageResponse<SelectedScreenResponse> querySelectedScreenSimple(ScreenQueryRequest request);


    /**
     * 画集详情查询
     * @param request
     * @return
     */
    PageResponse<ScreenSimpleResponse> querySelectedScreenSimpleDetail(CommonPageRequest request);


    /**
     * 画集详情查询
     * @param request
     * @return
     */
    ScreenSimpleDetailResponse queryScreenSimple(Map<String,Object> request);


    /**
     * 创建画作订单
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
     * 画集浏览次数查询接口
     * @param request
     * @return
     */
    BrowseCountResponse queryBrowseCount(CommonPageRequest request);
}
