package com.ihomefnt.o2o.service.dao.coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.coupon.CashCouponDao;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashAccountDo;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashCouponDo;
import com.ihomefnt.o2o.intf.domain.coupon.dto.CenterCouponDto;

@Repository
public class CashCouponDaoImpl implements CashCouponDao {
    private static final Logger LOG = LoggerFactory.getLogger(CashCouponDaoImpl.class);
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.coupon.CashCouponDao.";

    @Override
    public List<CashCouponDo> queryCouponByUserId(Map<String, Object> paramMap) {
        LOG.info("CashCouponDao.queryCouponByUserId() start");
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryCouponByUserId", paramMap);
    }

    @Override
    public int updateStatus(Map<String, Object> params) {
        LOG.info("CashCouponDao.updateStatus() start");
        return sqlSessionTemplate.update(NAME_SPACE + "updateStatus", params);
    }

    @Override
    public int updateAccountPay(Map<String, Object> params) {
        LOG.info("CashCouponDao.updateAccountPay() start");
        return sqlSessionTemplate.update(NAME_SPACE + "updateAccountPay", params);
    }

    @Override
    public int revokeAccountPay(Map<String, Object> params) {
        LOG.info("CashCouponDao.revokeAccountPay() start");
        return sqlSessionTemplate.update(NAME_SPACE + "revokeAccountPay", params);
    }

    @Override
    public int commitAccountPay(Map<String, Object> params) {
        LOG.info("CashCouponDao.commitAccountPay() start");
        return sqlSessionTemplate.update(NAME_SPACE + "commitAccountPay", params);
    }

    @Override
    public int updateUsableMoney(Map<String, Object> params) {
        LOG.info("CashCouponDao.updateUsableMoney() start");
        return sqlSessionTemplate.update(NAME_SPACE + "updateUsableMoney", params);
    }

    @Override
    public List<CashAccountDo> queryCashAccount(Long userId) {
        LOG.info("CashCouponDao.queryCashAccount() start");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryAccountMoney", paramMap);
    }

	@Override
	public List<CenterCouponDto> queryAllCoupons() {
		LOG.info("CashCouponDao.queryAllCoupons() start");
		return sqlSessionTemplate.selectList(NAME_SPACE+"queryAllCoupons");
	}
	
	@Override
	public List<CenterCouponDto> queryAllVouchers() {
		LOG.info("CashCouponDao.queryAllVouchers() start");
		return sqlSessionTemplate.selectList(NAME_SPACE+"queryAllVouchers");
	}
	
	@Override
	public String queryRemarkByVoucherId(Long voucherId) {
		LOG.info("CashCouponDao.queryRemarkByVoucherId() start");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("voucherId", voucherId);
		return sqlSessionTemplate.selectOne(NAME_SPACE+"queryRemarkByVoucherId", paramMap);
	}

	@Override
	public List<CenterCouponDto> queryUseableCoupons(String mobile) {
		LOG.info("CashCouponDao.queryUseableCoupons() start");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mobile", mobile);
		return sqlSessionTemplate.selectList(NAME_SPACE+"queryUseableCoupons", paramMap);
	}

	@Override
	public List<CenterCouponDto> queryUseableVouchers(String mobile) {
		LOG.info("CashCouponDao.queryUseableVouchers() start");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mobile", mobile);
		return sqlSessionTemplate.selectList(NAME_SPACE+"queryUseableVouchers", paramMap);
	}

	@Override
	public List<CenterCouponDto> queryVoucherType(String mobile, Long couponId) {
		LOG.info("CashCouponDao.queryVoucherType() start");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mobile", mobile);
		paramMap.put("couponId", couponId);
		return sqlSessionTemplate.selectList(NAME_SPACE+"queryVoucherType", paramMap);
	}

	@Override
	public int insertVoucher(String mobile, Long couponId) {
		LOG.info("CashCouponDao.insertVoucher() start");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mobile", mobile);
		paramMap.put("couponId", couponId);
		return sqlSessionTemplate.insert(NAME_SPACE+"insertVoucher", paramMap);
	}
	
	
}
