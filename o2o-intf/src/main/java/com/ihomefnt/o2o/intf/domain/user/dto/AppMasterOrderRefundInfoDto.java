package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单退款信息
 *
 * @author liyonggang
 * @create 2019-02-22 20:07
 */
@Data
public class AppMasterOrderRefundInfoDto {

    private BigDecimal refundAount;// 退款金额,

    private Integer refundStatus;//退款状态,

    private String refundTime;// 退款时间
}
