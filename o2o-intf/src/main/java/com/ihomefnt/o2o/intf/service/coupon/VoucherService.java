package com.ihomefnt.o2o.intf.service.coupon;

import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import com.ihomefnt.o2o.intf.domain.coupon.dto.VoucherLog;

import java.util.List;



/**
 * 抵用券查询服务
 * 
 * @author shenchen
 * @date 2016年3月23日 上午11:11:24
 */
public interface VoucherService {
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
	 * 根据用户手机号码查询当前能使用的抵用券
	 * @param mobile 手机号码
	 * @param amountPayable 应付金额
	 * @return
	 * @author shenchen
	 * @date 2016年3月25日 下午5:46:29
	 */
	public List<Voucher> getEnableVoucherList(String mobile,double amountPayable);
	
	/**
	 * 通过主键来查询抵用券实例
	 * @param pk
	 * @return
	 */
	 Voucher queryVoucherById(Long pk);
	 
	 Voucher queryVoucherByPK(Long pk);
	 
	 /**
	  * 通过主键来修改抵用券实例
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
