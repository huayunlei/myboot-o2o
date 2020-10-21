package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品方案订单支付详情
 * @author ZHAO
 */
@Data
public class OrderPayDetailListResponseVo {
    private Integer accountRecordId;//账本记录ID

    private String payMode;//支付方式: 支付宝、微信、现场支付

    private Integer stagePayment;//付款阶段类型

    private String stagePaymentDesc;//付款阶段描述

    private BigDecimal payAmount;//支付金额

    private String payResult;//支付结果

    private Date payTime;//支付时间
    
    private Integer aubitStatus;//0待确认收款  1已确认收款  2已驳回收款 12已退款

}
