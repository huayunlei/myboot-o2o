package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单退款信息
 *
 * @author ZHAO
 */
@Data
@ApiModel("订单退款信息")
public class OrderRefundInfo implements Serializable {

    @ApiModelProperty("退款状态:1 未打款  2已打款")
    private Integer refundStatus;//退款状态:1 未打款  2已打款

    @ApiModelProperty("退款状态说明")
    private String refundStatusDesc;//退款状态说明

    @ApiModelProperty("状态副文案")
    private String subStatusPraise;//状态副文案

    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;//退款金额

    @ApiModelProperty("退款时间")
    private String orderCancelTime;//订单取消时间

    @ApiModelProperty("取消时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date cancelTime;//订单取消时间

    @ApiModelProperty("退款说明")
    private List<String> refundDescList;//退款说明

    public OrderRefundInfo() {
        this.refundStatus = 0;
        this.refundStatusDesc = "";
        this.subStatusPraise = "";
        this.refundAmount = new BigDecimal(0);
        this.orderCancelTime = "";
        this.refundDescList = new ArrayList<>();
    }
}
