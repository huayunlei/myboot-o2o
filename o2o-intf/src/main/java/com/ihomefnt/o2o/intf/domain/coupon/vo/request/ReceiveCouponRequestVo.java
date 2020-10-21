package com.ihomefnt.o2o.intf.domain.coupon.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * 领取卡券请求参数
 *
 */
@Data
public class ReceiveCouponRequestVo extends HttpBaseRequest {
	private Long couponId;//券ID
	private Integer couponType;//券类型 1:现金券 2:抵用券
}
