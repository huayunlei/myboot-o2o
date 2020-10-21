package com.ihomefnt.o2o.intf.dao.coupon;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.coupon.doo.CashAccountDo;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashCouponDo;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CenterCouponDto;

public interface CashCouponDao {
	
	List<CashCouponDo> queryCouponByUserId(Map<String, Object> paramMap);
	
	List<CashAccountDo> queryCashAccount(Long userId);
	
	int updateStatus(Map<String, Object> params);
	
	int updateAccountPay(Map<String, Object> params);
	
	int revokeAccountPay(Map<String, Object> params);
	
	int commitAccountPay(Map<String, Object> params);
	
	int updateUsableMoney(Map<String, Object> params);
	
	//获取所有现金券
	List<CenterCouponDto> queryAllCoupons();
	
	//获取所有抵用券
	List<CenterCouponDto> queryAllVouchers();
	
	String queryRemarkByVoucherId(Long voucherId);
	
	//根据用户号码查询用户名下的现金券
	List<CenterCouponDto> queryUseableCoupons(String mobile);
	
	//根据用户号码查询用户名下的抵用券
	List<CenterCouponDto> queryUseableVouchers(String mobile);
	
	//查询抵用券的用户已领数和总数
	List<CenterCouponDto> queryVoucherType(String mobile,Long couponId);
	
	//插入用户手机号和券信息
	int insertVoucher(String mobile,Long couponId);
	
}
