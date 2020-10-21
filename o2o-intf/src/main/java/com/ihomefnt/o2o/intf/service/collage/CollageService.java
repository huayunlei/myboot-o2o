package com.ihomefnt.o2o.intf.service.collage;

import javax.servlet.http.HttpServletRequest;

import com.ihomefnt.o2o.intf.domain.collage.vo.request.CancelCollageOrderRequest;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.CreateCollageOrderRequest;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageActivityDetailRequest;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageMasterRequest;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryPayRequest;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryProductDetailRequest;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.CancelCollageOrderResponseVo;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.CollageMasterResponseVo;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.CollageOrderResponseVo;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.MainPageVo;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.OrderPayResultResponseVo;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.ProductDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.ProductFilterListResponseVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/16 9:29
 */
public interface CollageService {

    /**
     * 查询活动详情
     * @param req QueryCollageActivityDetailRequest
     * @return MainPageVo
     * @throws O2oException O2oException
     */
    MainPageVo queryCollageActivity(QueryCollageActivityDetailRequest req);

    /**
     * 查询团页面数据
     * @param req QueryCollageMasterRequest
     * @return CollageMasterResponseVo
     * @throws O2oException O2oException
     */
    CollageMasterResponseVo queryCollage(QueryCollageMasterRequest req);

    /**
     * 查询活动商品详情数据
     * @param req QueryProductDetailRequest
     * @return ProductDetailResponseVo ProductDetailResponseVo
     * @throws O2oException O2oException
     */
    ProductDetailResponseVo queryProductDetail(QueryProductDetailRequest req);


    /**
     * 拼团商品下单
     * @param req CreateCollageOrderRequest
     * @return CollageOrderResponseVo CollageOrderResponseVo
     * @throws O2oException O2oException
     */
    CollageOrderResponseVo createCollageOrder(CreateCollageOrderRequest req, HttpServletRequest httpServletRequest);


    /**
     * 拼团取消订单
     * @param req CancelCollageOrderRequest
     * @return
     * @throws O2oException
     */
    CancelCollageOrderResponseVo cancelCollageOrder(CancelCollageOrderRequest req);

    OrderPayResultResponseVo queryCollageOrder(QueryPayRequest req);


    ProductFilterListResponseVo  addSkuId2ProductFilterList(List<Integer> ids);

    ProductFilterListResponseVo moveSkuId2ProductFilterList(List<Integer> ids);

    ProductFilterListResponseVo loadSkuId2ProductFilterList();

	OrderResponse queryCollageOrderDetail(QueryCollageOrderDetailRequest req);

}
