package com.ihomefnt.o2o.api.controller.order;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderPayRecord;
import com.ihomefnt.o2o.intf.domain.order.dto.TOrder;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpOrderResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpSubOrderPayInfoResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HttpSubOrderPayResponse;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.service.sms.SmsService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shirely_geng on 15 - 1 - 22.
 */
@ApiIgnore
@Deprecated
@Api(tags = "订单老api", hidden = true)
@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    SmsService smsService;

    @ApiOperation(value = "queryOrderPay", notes = "queryOrderPay")
    @RequestMapping(value = "/queryOrderPay", method = RequestMethod.POST)
    public HttpBaseResponse<HttpOrderResponse> queryOrderPay(@Json HttpOrderRequest orderRequest) {
        if (orderRequest == null
                || orderRequest.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        TOrder order = orderService.queryMyOrderByOrderId(orderRequest.getOrderId());
        if (order == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.PRODUCT_ORDER_INVALID);
        }

        HttpOrderResponse orderResponse = new HttpOrderResponse();
        orderResponse.setOrderNum(order.getOrderNum());
        orderResponse.setOrderPrice(order.getOrderPrice());
        orderResponse.setCouponPay(order.getCouponPay());
        return HttpBaseResponse.success(orderResponse);
    }

    /**
     * 进入分笔支付页面
     */
    @ApiOperation(value = "createSubOrderPay", notes = "进入分笔支付页面")
    @RequestMapping(value = "/createSubOrderPay", method = RequestMethod.POST)
    public HttpBaseResponse<HttpSubOrderPayResponse> createSubOrderPay(@Json HttpOrderRequest orderRequest) {
        if (orderRequest == null
                || orderRequest.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }
        TOrder order = orderService.queryMyOrderByOrderId(orderRequest.getOrderId());
        if (order == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PRODUCT_NOT_EXISTS, MessageConstant.PRODUCT_ORDER_INVALID);
        }

        HttpSubOrderPayResponse orderResponse = new HttpSubOrderPayResponse();
        orderResponse.setOrderNum(order.getOrderNum());
        orderResponse.setOrderPrice(order.getOrderPrice());

        Double alreadyPay = orderService.queryPayedMoneyByOrderId(orderRequest.getOrderId());
        if (alreadyPay == null) {
            alreadyPay = 0d;
        }
        orderResponse.setAlreadyPay(alreadyPay);
        double remainpay = order.getOrderPrice() - alreadyPay;
        long l1 = Math.round(remainpay * 100);
        double ret = l1 / 100.0;

        orderResponse.setRemainPay(ret);
        List<Double> selectSum = orderService.querySelSum(10l);
        List<Double> allSum = new ArrayList<Double>();
        allSum.add(orderResponse.getRemainPay());
        if (null != selectSum) {
            for (Double d : selectSum) {
                if (d < orderResponse.getRemainPay()) {
                    allSum.add(d);
                }
            }
        }
        orderResponse.setSelectSum(allSum);

        return HttpBaseResponse.success(orderResponse);
    }

    /**
     * 查询支付明细
     */
    @ApiOperation(value = "querySubOrderPay", notes = "查询支付明细")
    @RequestMapping(value = "/querySubOrderPay", method = RequestMethod.POST)
    public HttpBaseResponse<HttpSubOrderPayInfoResponse> querySubOrderPay(@Json HttpOrderRequest orderRequest) {
        if (orderRequest == null
                || orderRequest.getOrderId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpUserInfoRequest userDto = orderRequest.getUserInfo();
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
        }

        List<OrderPayRecord> orderPayRecord = orderService.querySubOrderPay(orderRequest.getOrderId());

        HttpSubOrderPayInfoResponse response = new HttpSubOrderPayInfoResponse();
        response.setOrderPayRecord(orderPayRecord);
        response.setTotalRecords(orderPayRecord.size());
        return HttpBaseResponse.success(response);

    }


}