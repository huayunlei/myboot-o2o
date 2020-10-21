package com.ihomefnt.o2o.api.controller.order;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.common.http.PageModel;
import com.ihomefnt.o2o.intf.domain.order.vo.request.DeliverOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderListRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.*;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.service.order.ArtOrderService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author shirely_geng
 * @date 15 - 1 - 22
 */
@Api(tags = "【订单API】")
@RestController
@RequestMapping(value = "/artorder")
public class ArtOrderController {
	
	@Autowired
	OrderService orderService;
    @Autowired
	private ArtOrderService artOrderService;
	
	private static final Logger LOG = LoggerFactory.getLogger(ArtOrderController.class);

	/**
	 * 支付宝分笔支付，将支付信息提交给支付宝平台
	 * @param orderRequest
	 * @return
	 */
	@ApiOperation(value="getSubOrderforAlipay",notes="getSubOrderforAlipay")
	@RequestMapping(value = "/getSubOrderforAlipay", method = RequestMethod.POST)
	public HttpBaseResponse<HttpOrderResponse> getSubOrderforAlipay(@Json HttpOrderRequest orderRequest) {
		if (orderRequest == null || null == orderRequest.getOrderId()) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		HttpOrderResponse obj = artOrderService.getSubOrderforAlipay(orderRequest);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value="queryOrderPay",notes="queryOrderPay")
	@RequestMapping(value = "/queryOrderPay", method = RequestMethod.POST)
	public HttpBaseResponse<HttpOrderResponse> queryOrderPay(@Json HttpOrderRequest orderRequest) {
		if (orderRequest == null || StringUtil.isNullOrEmpty(orderRequest.getAccessToken())
				|| orderRequest.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		HttpOrderResponse obj = artOrderService.queryOrderPay(orderRequest);
		return HttpBaseResponse.success(obj);
	}

	/**
	 * 进入分笔支付页面
	 */
	@ApiOperation(value="createSubOrderPay",notes="进入分笔支付页面")
	@RequestMapping(value = "/createSubOrderPay", method = RequestMethod.POST)
	public HttpBaseResponse<HttpSubOrderPayResponse> createSubOrderPay(@Json HttpOrderRequest orderRequest) {
		if (orderRequest == null || StringUtil.isNullOrEmpty(orderRequest.getAccessToken())
				|| orderRequest.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		HttpSubOrderPayResponse obj = artOrderService.createSubOrderPay(orderRequest);
		return HttpBaseResponse.success(obj);
	}

	/**
	 * 查询支付明细
	 */
	@ApiOperation(value="querySubOrderPay",notes="查询支付明细")
	@RequestMapping(value = "/querySubOrderPay", method = RequestMethod.POST)
	public HttpBaseResponse<HttpSubOrderPayInfoResponse> querySubOrderPay(@Json HttpOrderRequest orderRequest) {
		if (orderRequest == null || StringUtil.isNullOrEmpty(orderRequest.getAccessToken())
				|| orderRequest.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		HttpSubOrderPayInfoResponse obj = artOrderService.querySubOrderPay(orderRequest);
		return HttpBaseResponse.success(obj);
	}

	/**
	 * 订单列表
	 * 
	 * @param orderRequest
	 * @version 20190725
	 * @return
	 */
	@ApiOperation(value="orderList295",notes="订单列表")
	@RequestMapping(value = "/orderList295", method = RequestMethod.POST)
	public HttpBaseResponse<PageModel> orderList295(@RequestBody HttpOrderListRequest orderRequest) {
		if (orderRequest == null || null == orderRequest.getAccessToken()) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		PageModel obj = artOrderService.orderList295(orderRequest);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value="物流信息查询接口",notes="物流信息查询接口")
	@RequestMapping(value = "/queryDeliveryInfoByOrderId", method = RequestMethod.POST)
	public HttpBaseResponse<OrderResponse> queryDeliveryInfoByOrderId(@RequestBody DeliverOrderRequest orderRequest) {
		return HttpBaseResponse.success(artOrderService.queryDeliveryInfoByOrderId(orderRequest));
	}
	
	/**
	 * 订单详情
	 * 
	 * @param orderRequest
	 * @return
	 */
	@ApiOperation(value="298/orderDetail",notes="订单详情")
	@RequestMapping(value = "/298/orderDetail", method = RequestMethod.POST)
	public HttpBaseResponse<OrderResponse> getOrderDetail298(@RequestBody HttpOrderRequest orderRequest) {
		if (orderRequest == null || orderRequest.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		OrderResponse order=null;
		if(orderRequest.getSaleType()!=null&&orderRequest.getSaleType()==1){
			//新版艾商城艺术品
			 order  = artOrderService.queryOrderDetail(orderRequest);
		}else{
			 order = artOrderService.getOrderDetail298(orderRequest);

		}
		return HttpBaseResponse.success(order);

	}
	
    /**
     * 取消订单
     */
	@ApiOperation(value="orderCancel",notes="取消订单")
	@RequestMapping(value = "/orderCancel", method = RequestMethod.POST)
	public HttpBaseResponse<Void> orderCancel(@Json HttpOrderRequest orderRequest) {
		artOrderService.orderCancel(orderRequest);
		return HttpBaseResponse.success();
	}
	
	@ApiOperation(value="getLogisticList",notes="查询订单所有物流列表信息")
	@RequestMapping(value = "/getLogisticList", method = RequestMethod.POST)
	public HttpBaseResponse<LogisticListResponse> getLogisticList(@RequestBody HttpOrderRequest request){
		if(request == null || request.getOrderId() == null){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		//根据订单ID查询所有物流信息
		LogisticListResponse response = orderService.queryOrderDeliveryInfoByOrderId(request.getOrderId().intValue(), request.getWidth());
		return HttpBaseResponse.success(response);
	}
	
	/**
	 * 支付宝分笔支付，将支付信息提交给支付宝平台
	 * @param orderRequest
	 * @return
	 */
	@ApiOperation(value="getSubOrderforAlipayReform",notes="getSubOrderforAlipayReform")
	@RequestMapping(value = "/getSubOrderforAlipayReform", method = RequestMethod.POST)
	public HttpBaseResponse<OrderPayInfoForAlipayResponseVo> getSubOrderforAlipayReform(@Json HttpOrderRequest orderRequest) {
		if (orderRequest == null || null == orderRequest.getOrderId()) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		OrderPayInfoForAlipayResponseVo obj = artOrderService.getSubOrderforAlipayReform(orderRequest);
		return HttpBaseResponse.success(obj);
	}

}