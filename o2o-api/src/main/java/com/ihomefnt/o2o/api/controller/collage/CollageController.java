package com.ihomefnt.o2o.api.controller.collage;

import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.*;
import com.ihomefnt.o2o.intf.domain.collage.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.service.collage.CollageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * @author jerfan cang
 * @date 2018/10/14 21:29
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/collage")
@Api(tags = "【拼团活动】", hidden = true)
public class CollageController {

    @Autowired
    private CollageService collageServer;

    @ApiOperation(value = "拼团活动主页", notes = "活动主页")
    @RequestMapping(value = "/main/page", method = RequestMethod.POST)
    public HttpBaseResponse<MainPageVo> mainPage(@RequestBody QueryCollageActivityDetailRequest req) {
        if (req == null || null == req.getActivityId() || StringUtil.isEmpty(req.getOpenid())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        MainPageVo mainPageVo = collageServer.queryCollageActivity(req);
        return HttpBaseResponse.success(mainPageVo);
    }

    @ApiOperation(value = "团页面", notes = "团页面")
    @RequestMapping(value = "/master/page", method = RequestMethod.POST)
    public HttpBaseResponse<CollageMasterResponseVo> collagePage(@RequestBody QueryCollageMasterRequest req) {
        if (req == null || null == req.getActivityId() || null == req.getGroupId() || StringUtil.isEmpty(req.getOpenid())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        CollageMasterResponseVo collageMasterResponseVo = collageServer.queryCollage(req);
        return HttpBaseResponse.success(collageMasterResponseVo);
    }

    @ApiOperation(value = "拼团商品详情页面", notes = "拼团商品详情页面")
    @RequestMapping(value = "/product/detail", method = RequestMethod.POST)
    public HttpBaseResponse<ProductDetailResponseVo> activityProduct(@RequestBody QueryProductDetailRequest req) {
        if (req == null || null == req.getActivityId() || StringUtil.isEmpty(req.getOpenid())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        ProductDetailResponseVo productDetail = collageServer.queryProductDetail(req);
        return HttpBaseResponse.success(productDetail);
    }

    @ApiOperation(value = "拼团创建订单", notes = "拼团创建订单")
    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public HttpBaseResponse<CollageOrderResponseVo> createCollageOrder(@RequestBody CreateCollageOrderRequest req, HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            if (!(httpServletRequest.getHeader("user-agent").contains("MicroMessenger") ||
                    httpServletRequest.getHeader("user-agent").equals("micromessenger"))) {
                return HttpBaseResponse.fail("非微信环境");
            }
        }

        if (req == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (StringUtil.isEmpty(req.getOpenid())) {
            return HttpBaseResponse.fail("微信登录标识openid未获取到");
        }
        if (StringUtil.isEmpty(req.getMobile())) {
            return HttpBaseResponse.fail("下单人手机号不能为空");
        }
        if (req.isPayMark()) {
            if (StringUtil.isEmpty(req.getAccessToken())) {
                return HttpBaseResponse.fail("使用艾积分支付，请先登录");
            }
        }

        CollageOrderResponseVo collageOrderResponseVo = collageServer.createCollageOrder(req, httpServletRequest);
        return HttpBaseResponse.success(collageOrderResponseVo);
    }

    @ApiOperation(value = "拼团查询订单支付结果", notes = "拼团查询订单支付结果")
    @RequestMapping(value = "/order/query", method = RequestMethod.POST)
    public HttpBaseResponse<OrderPayResultResponseVo> queryCollageOrder(@RequestBody QueryPayRequest req) {
        if (req == null || null == req.getActivityId() || StringUtil.isEmpty(req.getOpenid())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        OrderPayResultResponseVo payResultResponseVo = collageServer.queryCollageOrder(req);
        return HttpBaseResponse.success(payResultResponseVo);
    }

    @ApiOperation(value = "拼团取消未支付订单", notes = "拼团取消未支付订单")
    @RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
    public HttpBaseResponse<CancelCollageOrderResponseVo> cancelCollageOrder(@RequestBody CancelCollageOrderRequest req) {
        if (req == null || null == req.getOrderId() || StringUtil.isEmpty(req.getReceiveTel())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        CancelCollageOrderResponseVo orderResponse = collageServer.cancelCollageOrder(req);
        return HttpBaseResponse.success(orderResponse);
    }

    @ApiOperation(value = "拼团成功订单详情查询", notes = "拼团成功订单详情查询")
    @RequestMapping(value = "/order/detail/query", method = RequestMethod.POST)
    public HttpBaseResponse<OrderResponse> queryCollageOrderDetail(@RequestBody QueryCollageOrderDetailRequest req) {
        if (req == null || null == req.getOrderId() || StringUtil.isEmpty(req.getOpenid())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        OrderResponse orderResponse = collageServer.queryCollageOrderDetail(req);
        return HttpBaseResponse.success(orderResponse);
    }


    @ApiOperation(value = "添加商品到过滤列表", notes = "添加商品到过滤列表")
    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public HttpBaseResponse<ProductFilterListResponseVo> addProduct2FilterList(@RequestBody ProductFilterRequest req) {
        if (null == req || StringUtil.isEmpty(req.getNickName()) || null == req.getSign() || null == req.getSkuIds()) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (!req.getNickName().equals(req.getSign())) {
            return HttpBaseResponse.fail("校验失败");
        }
        if (req.getSkuIds().size() <= 0) {
            return HttpBaseResponse.fail("没有要操作的数据");
        }

        ProductFilterListResponseVo filterListResponseVo = collageServer.addSkuId2ProductFilterList(req.getSkuIds());
        return HttpBaseResponse.success(filterListResponseVo);
    }

    @ApiOperation(value = "查询过滤商品列表", notes = "查询过滤商品列表")
    @RequestMapping(value = "/product/load", method = RequestMethod.POST)
    public HttpBaseResponse<ProductFilterListResponseVo> loadProduct2FilterList(@RequestBody ProductFilterRequest req) {
        if (null == req || StringUtil.isEmpty(req.getNickName()) || null == req.getSign() || null == req.getSkuIds()) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (!req.getNickName().equals(req.getSign())) {
            return HttpBaseResponse.fail("校验失败");
        }
        if (req.getSkuIds().size() <= 0) {
            return HttpBaseResponse.fail("没有要操作的数据");
        }

        ProductFilterListResponseVo filterListResponseVo = collageServer.loadSkuId2ProductFilterList();
        return HttpBaseResponse.success(filterListResponseVo);
    }

    @ApiOperation(value = "删除商品从过滤列表", notes = "删除商品从过滤列表")
    @RequestMapping(value = "/product/move", method = RequestMethod.POST)
    public HttpBaseResponse<ProductFilterListResponseVo> moveProduct2FilterList(@RequestBody ProductFilterRequest req) {
        if (null == req || StringUtil.isEmpty(req.getNickName()) || null == req.getSign() || null == req.getSkuIds()) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (!req.getNickName().equals(req.getSign())) {
            return HttpBaseResponse.fail("校验失败");
        }
        if (req.getSkuIds().size() <= 0) {
            return HttpBaseResponse.fail("没有要操作的数据");
        }

        ProductFilterListResponseVo filterListResponseVo = collageServer.moveSkuId2ProductFilterList(req.getSkuIds());
        return HttpBaseResponse.success(filterListResponseVo);
    }

}
