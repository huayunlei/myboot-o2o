package com.ihomefnt.o2o.api.controller.artist;

import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarOrderCreateRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.StarOrderResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderListRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpSubmitOrderResponse;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.art.ArtService;
import com.ihomefnt.o2o.intf.service.artist.ArtistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author huayunlei
 * @created 2018年10月13日 下午9:34:45
 * @desc 
 */
@ApiIgnore
@RestController
@Api(tags = "【微信小程序】小星星订单API",hidden = true)
@RequestMapping("/starOrder")
public class StarOrderController {
	
	@Autowired
	private ArtService artService;
	
	@Autowired
	private ArtistService artistService;

	/**
     * 创建小星星订单
     */
	@ApiOperation(value = "创建小星星订单", notes = "创建小星星订单")
    @RequestMapping(value = "/createStarOrder", method = RequestMethod.POST)
    public HttpBaseResponse<HttpSubmitOrderResponse> createStarOrder(@RequestBody StarOrderCreateRequest request) {
		if (request == null || StringUtils.isBlank(request.getAccessToken())
                || CollectionUtils.isEmpty(request.getProductList()) || request.getSoftContractgMoney() == null
                || request.getOsType() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        request.setShoppingCartPay(false);

		HttpSubmitOrderResponse result = artService.createStarOrder(request);
		return HttpBaseResponse.success(result);
	}
	
	/**
	 * 小星星订单列表
	 * 
	 * @param orderRequest
	 * @return
	 */
	@ApiOperation(value = "小星星订单列表")
	@RequestMapping(value = "/starOrderList", method = RequestMethod.POST)
	public HttpBaseResponse<PageModel<StarOrderResponse>> starOrderList(@Json HttpOrderListRequest orderRequest) {
		if (orderRequest == null || null == orderRequest.getAccessToken()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
		}
		PageModel<StarOrderResponse> baseResponse = artistService.getMyStarOrderList(orderRequest);

		return HttpBaseResponse.success(baseResponse);
	}
	
	
	
	@ApiOperation(value = "小星星订单详情")
	@RequestMapping(value = "/getStarOrderDetail", method = RequestMethod.POST)
	public HttpBaseResponse<StarOrderResponse> getOrderDetail298(@Json HttpOrderRequest orderRequest) {
		if (orderRequest == null || orderRequest.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		StarOrderResponse baseResponse = artistService.getMyStarOrderDetail(orderRequest);
		return HttpBaseResponse.success(baseResponse);
	}

}
