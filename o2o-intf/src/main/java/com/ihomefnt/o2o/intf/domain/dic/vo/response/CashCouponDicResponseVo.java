package com.ihomefnt.o2o.intf.domain.dic.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.coupon.dto.CouponRemarkDto;

import lombok.Data;

@Data
public class CashCouponDicResponseVo {

	private List<CouponRemarkDto> remarkList;
	
}
