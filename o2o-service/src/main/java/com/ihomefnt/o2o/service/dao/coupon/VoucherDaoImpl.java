package com.ihomefnt.o2o.service.dao.coupon;

import com.ihomefnt.o2o.intf.dao.coupon.VoucherDao;
import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import com.ihomefnt.o2o.intf.domain.coupon.dto.VoucherLog;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class VoucherDaoImpl implements VoucherDao {
	
	@Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher.";
	
	/**
	 * 根据用户手机号码查询所有抵用券
	 * @author shenchen
	 * @date 2016年3月23日 上午10:25:45
	 * @describe 只查询有效期截止日小于1个月的
	 */
	@Override
	public List<Voucher> queryVoucherByMobile(String mobile) {
		return this.sqlSessionTemplate.selectList(NAME_SPACE+"queryVoucherByMobile",mobile);
	}

	/**
	 * 根据用户手机号码统计抵用券总额
	 * @author shenchen
	 * @date 2016年3月23日 上午10:45:16
	 * @describe 只统计可用的抵用券
	 */
	@Override
	public double queryVoucherAmountByMobile(String mobile) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE+"queryVoucherAmountByMobile",mobile);
	}
	
	@Override
	public Voucher queryVoucherById(Long pk){
		return this.sqlSessionTemplate.selectOne(NAME_SPACE+"queryVoucherById",pk);
	}
	
	public Voucher queryVoucherByPK(Long pk){
		return this.sqlSessionTemplate.selectOne(NAME_SPACE+"queryVoucherByPK",pk);
	}
	
	@Override
	public int updateVoucherById(Voucher voucher){
		return this.sqlSessionTemplate.update(NAME_SPACE+"updateVoucherById",voucher);
	}

	
	@Override
	public int insertVoucherLog(VoucherLog log){
		return this.sqlSessionTemplate.insert(NAME_SPACE+"insertVoucherLog",log);
	}


}
