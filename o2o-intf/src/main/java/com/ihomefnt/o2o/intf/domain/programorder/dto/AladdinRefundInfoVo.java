package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 全品家大订单退款信息
 * @author ZHAO
 */
@Data
public class AladdinRefundInfoVo {
	private BigDecimal refundAount;//退款金额
	
	private Integer refundStatus;//退款状态:10待审批退款  11待退款  12已退款
	
	private Date refundTime;//退款时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
	private Date cancelTime;//取消时间
}
