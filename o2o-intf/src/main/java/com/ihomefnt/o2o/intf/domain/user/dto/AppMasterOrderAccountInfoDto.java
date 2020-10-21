package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 只订单款项信息
 *
 * @author liyonggang
 * @create 2019-02-22 19:58
 */
@Data
public class AppMasterOrderAccountInfoDto {

    private BigDecimal totalAmount;// 收款总金额,

    private BigDecimal confirmedAmount;// 已确认金额,

    private BigDecimal confirmingAmount;// 待确认金额,

    private BigDecimal refundedAmount;// 已确认退款金额,

    private BigDecimal refundingAmount;// 待确认退款金额,

    private BigDecimal totalRefundAmount;// 总退款金额,

    private Date lastUpdateTime;// 最后的更新时间,

    private Date orderTime;// 下单时间（app下单时间）,

    private BigDecimal fundProcess;// 收款进度
}
