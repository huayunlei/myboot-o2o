package com.ihomefnt.o2o.intf.service.coupon;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.coupon.doo.CashAccountDo;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CenterCouponDto;
import com.ihomefnt.o2o.intf.domain.coupon.vo.request.CashCouponRequestVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.response.CashCouponResponseVo;

public interface CashCouponService {

	CashCouponResponseVo queryCouponByUserId(Long userId,Long isRead);
	
	List<CashAccountDo> queryCashAccount(Long userId);
	
    int updateStatus(CashCouponRequestVo request);
	
	int modifyAccountPay(Long userId,Double couponPay,int type);
	
	List<CenterCouponDto> getAllCoupons();//查询所有可用的优惠券
	
	List<CenterCouponDto> getUseableCoupons(String mobile);//根据用户手机号查询用户可用的优惠券
	
	int updateVoucher(String mobile,int couponType,Long couponId);//插入抵用券
	
}
