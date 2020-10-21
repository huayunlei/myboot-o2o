package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jerfan cang
 * @date 2018/10/19 20:49
 */
@Data
@ApiModel("CollageOrderBaseInfoVo")
public class CollageOrderBaseInfoVo {

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("订单编号")
    private String orderNum;

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("收货人姓名")
    private String receiveName;

    @ApiModelProperty("收货人电话")
    private  String receiveTel;

    @ApiModelProperty("收货人地址")
    private String receiveAddress;

    @ApiModelProperty("支付方式")
    private Integer payType;

    @ApiModelProperty("实际支付金额")
    private BigDecimal truePayment;

    @ApiModelProperty("商品总价")
    private BigDecimal pirce;

    @ApiModelProperty("运费")
    private BigDecimal logistyPrice;

    @ApiModelProperty("本订单艾积分使用数量")
    private Integer aijiabiAmount;

    @ApiModelProperty("下单时间")
    private Long createOrderTime;

    @ApiModelProperty("支付时间")
    private Long payTime;

}
