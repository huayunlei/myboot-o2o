package com.ihomefnt.o2o.service.service.coupon;

import com.ihomefnt.o2o.intf.dao.coupon.CashCouponDao;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashAccountDo;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashCouponDo;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CenterCouponDto;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CouponRemarkDto;
import com.ihomefnt.o2o.intf.domain.coupon.vo.request.CashCouponRequestVo;
import com.ihomefnt.o2o.intf.domain.coupon.vo.response.CashCouponResponseVo;
import com.ihomefnt.o2o.intf.service.coupon.CashCouponService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CashCouponServiceImpl implements CashCouponService {

	private static final int SUCCESS_GET = 1;//领券成功
	private static final int FAILED_GET = 2;//领券失败
	private static final int NO_VOUCHER_EXIST = 3;//券已领完
	private static final int VOUCHER_OVERTIME = 4;//券已过期

	@Autowired
	CashCouponDao couponDao;
	
	@Override
	public CashCouponResponseVo queryCouponByUserId(Long userId, Long isRead) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("isRead", isRead);
		List<CashCouponDo> cashCouponList = couponDao.queryCouponByUserId(paramMap);
        SimpleDateFormat time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

		for(CashCouponDo cashCoupon : cashCouponList){
			if (StringUtils.isNotBlank(cashCoupon.getRemark())) {
				
				String[] sets = cashCoupon.getRemark().split("<question>");
				List<CouponRemarkDto> remarkList = new ArrayList<CouponRemarkDto>();
				for(int i=1;i<sets.length;i++){
					CouponRemarkDto couponRemark = new CouponRemarkDto();
					String[] set = sets[i].split("<answer>");
					couponRemark.setQuestion(set[0]);
					couponRemark.setAnswer(set[1]);
					remarkList.add(couponRemark);
				}
				if(cashCoupon.getCreateTimestamp()!=null){
					cashCoupon.setCreateTime(time.format(cashCoupon.getCreateTimestamp()));
				}
				cashCoupon.setRemarkList(remarkList);
				
	        }
		}
		
		CashCouponResponseVo cashCouponResponse = new CashCouponResponseVo();
		cashCouponResponse.setCashCouponList(cashCouponList);
		cashCouponResponse.setCashAccount(couponDao.queryCashAccount(userId));
		return cashCouponResponse;
	}

	@Override
	public int updateStatus(CashCouponRequestVo request) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(null != request.getCouponId()){
			for(Long cid : request.getCouponId()){
				params.put("couponId", cid);
				couponDao.updateStatus(params);
			}
		}
		return 0;
	}

	@Override
	public int modifyAccountPay(Long userId, Double couponPay,int type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("couponPay", couponPay);
		if(type == 1){
			return couponDao.updateAccountPay(params);
		}
		if(type == 2){
			return couponDao.revokeAccountPay(params);
		}
		if(type == 3){
			return couponDao.commitAccountPay(params);
		}
		return 0;
	}

	@Override
	public List<CashAccountDo> queryCashAccount(Long userId) {
		return couponDao.queryCashAccount(userId);
	}

	/**
	 * 获取数据库中所有券
	 */
	@Override
	public List<CenterCouponDto> getAllCoupons() {
		//存放所有的券：现金券，抵用券
		List<CenterCouponDto> conponResultList = new ArrayList<CenterCouponDto>();
		
		//获取所有抵用券
		List<CenterCouponDto> voucherList = couponDao.queryAllVouchers();
		for (CenterCouponDto centerCoupon : voucherList) {
			centerCoupon.setCouponType(2);
			List<CouponRemarkDto> couponRemark = getCouponRemark(centerCoupon.getRemark());
			centerCoupon.setVoucherRemarkList(couponRemark);
			if(Integer.parseInt(centerCoupon.getPayMoney())>0){
				centerCoupon.setPayment(2);
			}else {
				centerCoupon.setPayment(1);
			}
			centerCoupon.setMoneyLimitDesc("满"+centerCoupon.getMoneyLimitDesc()+"可用");
			String str = centerCoupon.getCouponImage();
			centerCoupon.setCouponImage(str.substring(2, str.length()-2).toString());
			centerCoupon.setStatus(1);
			String startTime = centerCoupon.getTimeStart().split(" ")[0].replace("-", ".").toString();
			String endTime = centerCoupon.getTimeEnd().split(" ")[0].replace("-", ".").toString();
			centerCoupon.setTimeDesc(startTime+"-"+endTime);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date currentDate = df.parse(df.format(new Date()).toString());
				Date startDate = df.parse(centerCoupon.getTimeStartReceive());
				Date endDate = df.parse(centerCoupon.getTimeEndReceive());
				if(currentDate.after(startDate) && currentDate.before(endDate)){
					conponResultList.add(centerCoupon);
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
		}
		
		return conponResultList;
	}

	/**
	 * 当用户登录时，查询用户的券
	 */
	@Override
	public List<CenterCouponDto> getUseableCoupons(String mobile) {
		//存放所有查询结果
		List<CenterCouponDto> conponResultList = new ArrayList<CenterCouponDto>();
		
		//查询对应id下的抵用券
		List<CenterCouponDto> voucherList = couponDao.queryUseableVouchers(mobile);
		if(voucherList != null && voucherList.size() > 0){
			for (CenterCouponDto centerCoupon : voucherList) {
				centerCoupon.setCouponType(2);
				List<CouponRemarkDto> couponRemark = getCouponRemark(centerCoupon.getRemark());
				centerCoupon.setVoucherRemarkList(couponRemark);
				if(Float.parseFloat(centerCoupon.getPayMoney())>0) {
					centerCoupon.setPayment(2);
				}else {
					centerCoupon.setPayment(1);
				}
				if(null == centerCoupon.getCurrentCount()){
					centerCoupon.setCurrentCount((long) 0);
				}
				if(centerCoupon.getCurrentCount()<centerCoupon.getMaxCount()){
					centerCoupon.setStatus(1);
				}else {
					centerCoupon.setStatus(2);
				}
				String str = centerCoupon.getCouponImage();
				centerCoupon.setCouponImage(str.substring(2, str.length()-2).toString());
				centerCoupon.setMoneyLimitDesc("满"+centerCoupon.getMoneyLimitDesc()+"可用");
				String startTime = centerCoupon.getTimeStart().split(" ")[0].replace("-", ".").toString();
				String endTime = centerCoupon.getTimeEnd().split(" ")[0].replace("-", ".").toString();
				centerCoupon.setTimeDesc(startTime+"-"+endTime);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date currentDate = df.parse(df.format(new Date()).toString());
					Date startDate = df.parse(centerCoupon.getTimeStartReceive());
					Date endDate = df.parse(centerCoupon.getTimeEndReceive());
					if(currentDate.after(startDate) && currentDate.before(endDate)){
						conponResultList.add(centerCoupon);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} 
		
		return conponResultList;
	}

	private List<CouponRemarkDto> getCouponRemark(String remarkStr){
		if (StringUtils.isNotBlank(remarkStr)) {				
			String[] remarks = remarkStr.split("<question>");
			List<CouponRemarkDto> voucherRemarkList = new ArrayList<CouponRemarkDto>();
			for(int i=1;i<remarks.length;i++){
				CouponRemarkDto remark = new CouponRemarkDto();
				String[] set = remarks[i].split("<answer>");
				if(set.length==2){
					remark.setQuestion(set[0]);
					remark.setAnswer(set[1]);
					voucherRemarkList.add(remark);
				}								
			}
			return voucherRemarkList;
		}else {
			return null;
		}
		
	}

	/**
	 * 根据mobile,券类型,券id,把券插入数据库
	 */
	@Override
	public synchronized int updateVoucher(String mobile, int couponType, Long couponId) {
		//当为抵用券时,先查询该号码下,该券是否已领完
		if(couponType == 2){
			List<CenterCouponDto> voucherList = couponDao.queryVoucherType(mobile,couponId);
			CenterCouponDto centerCoupon = null;
			if(voucherList != null && voucherList.size() > 0){
				centerCoupon = voucherList.get(0);
				if(null == centerCoupon.getCurrentCount()){
					centerCoupon.setCurrentCount((long) 0);
				}
				if(centerCoupon.getCurrentCount() < centerCoupon.getMaxCount()){
					int state = couponDao.insertVoucher(mobile,couponId);
					if(state > 0){
						return SUCCESS_GET;//领取成功
					}else {
						return FAILED_GET;//领取失败
					}
				}else {
					return NO_VOUCHER_EXIST;//券已领完
				}
				
			} else {
				return VOUCHER_OVERTIME;//券已过期
			}
		}
		return 2;
	}

	
	
	
	
}
