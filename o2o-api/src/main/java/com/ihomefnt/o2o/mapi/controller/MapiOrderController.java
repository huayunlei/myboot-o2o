package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.common.config.WebConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value="M站订单API",description="M站订单老接口",tags = "【M-API】")
@RestController
@RequestMapping("/mapi/order")
public class MapiOrderController {

    private static final Logger LOG = LoggerFactory.getLogger(MapiOrderController.class);
    
    @Autowired
    OrderService orderService;

    @Autowired
    private WebConfig webConfig;

    /**
     * 银联支付
     * @return
     */
    @ApiOperation(value="goUnionPay",notes="银联支付")
	@RequestMapping(value = "/goUnionPay", method = RequestMethod.POST)
	public HttpBaseResponse<String> goUnionPay(@RequestParam(value = "orderId", required = false) Long orderId,
	   		@RequestParam(value = "accessToken", required = false) String accessToken) {

	    if (null == orderId || StringUtil.isNullOrEmpty(accessToken)) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String html = orderService.goUnionPay(orderId, accessToken, webConfig.HOST);
        return HttpBaseResponse.success(html, MessageConstant.SUCCESS);
	}
	
}
