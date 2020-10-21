package com.ihomefnt.o2o.intf.domain.coupon.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class CashCouponRequestVo extends HttpBaseRequest {

	private List<Long> couponId;
	
	private Double amountPayable; //本次支付金额
	
	private Long isRead;

}
