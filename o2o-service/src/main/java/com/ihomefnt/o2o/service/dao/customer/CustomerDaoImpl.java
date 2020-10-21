package com.ihomefnt.o2o.service.dao.customer;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.customer.CustomerDao;
import com.ihomefnt.o2o.intf.domain.customer.doo.CommissionInventoryDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerDetailDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerItemDo;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.customer.";

	@Override
	public int getCountByAdviser(long adviserId) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getCountByAdviser", adviserId);
	}
	
	@Override
	public int getStoreCountByAdviser(long adviserId) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getStoreCountByAdviser", adviserId);
	}
	
	@Override
	public double getOrderPriceTotalByAdviser(long adviserId) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getOrderPriceTotalByAdviser", adviserId);
	}
	
	@Override
	public List<CustomerItemDo> getCustomerListByAdviser(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.selectList(NAME_SPACE + "getCustomerListByAdviser", paramMap);
	}
	
	@Override
	public CustomerDetailDo getCustomerByPK(long customerId){
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getCustomerByPK", customerId);
	}
	
	@Override
	public CustomerDetailDo getCustomerByMobile(String mobile){
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getCustomerByMobile", mobile);
	}

	@Override
	public int getCustomerCountByMobile(String mobile) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getCustomerCountByMobile", mobile);
	}

	@Override
	public int getOrderCountByMobile(String mobile) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getOrderCountByMobile", mobile);
	}

	@Override
	public int getFamilyOrderCountByMobile(String mobile) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getFamilyOrderCountByMobile", mobile);
	}

	@Override
	public int getAppCustomerCountByMobile(String mobile) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getAppCustomerCountByMobile", mobile);
	}

	@Override
	public int getExceeded(long adviserId) {
		return this.sqlSessionTemplate.selectOne(NAME_SPACE + "getExceededByAdviser", adviserId);
	}

	@Override
	public int invitingCustomer(CustomerDo request) {
		return this.sqlSessionTemplate.insert(NAME_SPACE + "invitingCustomer", request);
	}

	@Override
	public List<CommissionInventoryDo> getCommissionInventoryListByAdviser(long adviserId) {
		return this.sqlSessionTemplate.selectList(NAME_SPACE + "getCommissionInventoryListByAdviser", adviserId);
	}

}
