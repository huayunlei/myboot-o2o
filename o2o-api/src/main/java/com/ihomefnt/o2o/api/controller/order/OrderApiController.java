package com.ihomefnt.o2o.api.controller.order;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.request.OrderAuthRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderAuthResponseVo;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author hua
 * @Date 2020/1/13 9:54 上午
 */
@RestController
@RequestMapping("/order")
@Api(tags = "【订单相关API】")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "订单鉴权", notes = "订单鉴权")
    @PostMapping("/orderAuth")
    public HttpBaseResponse<OrderAuthResponseVo> orderAuth(@RequestBody OrderAuthRequestVo request) {
        return HttpBaseResponse.success(orderService.orderAuth(request));
    }
}
