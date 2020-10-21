package com.ihomefnt.o2o.intf.dao.customer;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.customer.doo.CommissionInventoryDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerDetailDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerItemDo;

public interface CustomerDao {

	/**
	 * 查询置业顾问下所有客户数量
	 * 
	 * @param adviserId
	 *            置业顾问编号
	 * @return
	 */
	public int getCountByAdviser(long adviserId);
	
	/**
	 * 查询置业顾问下所有到店的客户数量
	 * 
	 * @param adviserId
	 *            置业顾问编号
	 * @return
	 */
	public int getStoreCountByAdviser(long adviserId);
	
	/**
	 * 查询置业顾问下所有客户
	 * 
	 * @param adviserId
	 *            置业顾问编号
	 * @return
	 */
	List<CustomerItemDo> getCustomerListByAdviser(Map<String, Object> paramMap);	
	
	/**
	 * 根据客户id获取客户信息
	 * @param customerId
	 * @return
	 */
	CustomerDetailDo getCustomerByPK(long customerId);	
	
	/**
	 * 根据客户号码获取客户信息
	 * @param customerId
	 * @return
	 */
	CustomerDetailDo getCustomerByMobile(String mobile);	

	/**
	 * 根据手机号码查询boss用户跟进记录数
	 * 
	 * @param mobile
	 * @return
	 */
	public int getCustomerCountByMobile(String mobile);

	/**
	 * 根据手机号码查询软订单数量
	 * 
	 * @param mobile
	 * @return
	 */
	public int getOrderCountByMobile(String mobile);

	/**
	 * 根据手机号码查询全品家订单数量
	 * 
	 * @param mobile
	 * @return
	 */
	public int getFamilyOrderCountByMobile(String mobile);

	/**
	 * 根据手机号码查询app用户数量
	 * 
	 * @param mobile
	 * @return
	 */
	public int getAppCustomerCountByMobile(String mobile);

	/**
	 * 查询该顾问今天邀请用户数据
	 * 
	 * @param adviserId
	 *            置业顾问编号
	 * @return
	 * 
	 */
	public int getExceeded(long adviserId);

	/**
	 * 邀请用户
	 * 
	 * @param request
	 * @return
	 */
	public int invitingCustomer(CustomerDo request);
	
	/**
	 * 获取顾问的交易佣金
	 * @param adviserId
	 * @return
	 */
	double getOrderPriceTotalByAdviser(long adviserId);
	
	/**
	 * 获取顾问所有已经到店用户明细
	 * @param adviserId
	 * @return
	 */
	List<CommissionInventoryDo> getCommissionInventoryListByAdviser(long adviserId);

}
