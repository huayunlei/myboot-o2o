package com.ihomefnt.o2o.intf.dao.coupon;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import com.ihomefnt.o2o.intf.domain.coupon.dto.VoucherLog;



/**
 * 抵用券DAO
 * 
 * @author shenchen
 * @date 2016年3月23日 上午10:12:57
 */
public interface VoucherDao {
	/**
	 * 根据用户手机号码查询所有抵用券
	 * @param mobile 用户手机号码
	 * @return
	 * @author shenchen
	 * @date 2016年3月23日 上午10:14:20
	 */
	public List<Voucher> queryVoucherByMobile(String mobile);
	
	/**
	 * 根据用户手机号码统计抵用券总额
	 * @param mobile 用户手机号码
	 * @return
	 * @author shenchen
	 * @date 2016年3月23日 上午10:15:52
	 */
	public double queryVoucherAmountByMobile(String mobile);
	
	/**
	 * 通过主键来查询抵用券实例
	 * @param pk
	 * @return
	 */
	 Voucher queryVoucherById(Long pk);
	 
	 Voucher queryVoucherByPK(Long pk);
	 
	 /**
	  * 通过主键来修改抵用券实例
	  * @param pk
	  * @return
	  */
	 int updateVoucherById(Voucher voucher);
	 
	 /**
	  * 插入抵用券日志
	  * @param log
	  * @return
	  */
	 int insertVoucherLog(VoucherLog log);
}
