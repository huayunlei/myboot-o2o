package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付结果返回数据
 *
 * @author ZHAO
 */
@ApiModel("支付结果返回数据")
@Data
public class PaymentResultResponse {

    @ApiModelProperty("订单ID")
    private Integer orderId;//订单ID

    @ApiModelProperty("订单编号")
    private String orderNum;//订单编号

    @ApiModelProperty("付款阶段类型：1定金 2首付款 3中期款 4尾款")
    private Integer stagePayment;// 付款阶段类型：1定金 2首付款 3中期款 4尾款

    @ApiModelProperty("付款阶段描述")
    private String stagePaymentDesc;// 付款阶段描述

    @ApiModelProperty("阶段支付最低比例")
    private BigDecimal minStagePayRate;// 阶段支付最低比例

    @ApiModelProperty("阶段支付最高比例")
    private BigDecimal maxStagePayRate;//阶段支付最高比例

    @ApiModelProperty("阶段支付最低比例")
    private String minStagePayRateDesc;// 阶段支付最低比例

    @ApiModelProperty("阶段支付最高比例")
    private String maxStagePayRateDesc;//阶段支付最高比例

    @ApiModelProperty("阶段应付总额")
    private BigDecimal stageTotalAmount;// 阶段应付总额 

    @ApiModelProperty("阶段已支付金额")
    private BigDecimal stageFillAmount;// 阶段已支付金额

    @ApiModelProperty("阶段剩余应支付金额")
    private BigDecimal stageRemainAmount;// 阶段剩余应支付金额

    @ApiModelProperty("订单状态，取值：13：接触阶段，14：意向阶段，15：定金阶段，16：签约阶段，17：交付中，2：已完成，3：已取消")
    private Integer orderStatus;//  订单状态，取值：13：接触阶段，14：意向阶段，15：定金阶段，16：签约阶段，17：交付中，2：已完成，3：已取消

    @ApiModelProperty("默认订金")
    private BigDecimal depositMoneyDefalut;//默认订金

    @ApiModelProperty("剩余订金")
    private BigDecimal depositMoneyLeft;//剩余订金

    @ApiModelProperty("订单总价")
    private CopyWriterAndValue<String, BigDecimal> finalOrderPrice;//订单总价

}
