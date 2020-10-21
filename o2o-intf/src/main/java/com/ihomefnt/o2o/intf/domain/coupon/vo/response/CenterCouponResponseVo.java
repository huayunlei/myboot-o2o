package com.ihomefnt.o2o.intf.domain.coupon.vo.response;

import com.ihomefnt.o2o.intf.domain.coupon.dto.CenterCouponDto;
import lombok.Data;

import java.util.List;

/**
 * 卡券中心返回值：1.发券中心的头图，2.可售券列表
 * @author Administrator
 *
 */
@Data
public class CenterCouponResponseVo  {
	private String headImage;//购券中心头图
	private List<CenterCouponDto> couponList;//可售券的列表
}
