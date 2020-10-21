package com.ihomefnt.o2o.intf.service.customer;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerQueryRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.CommissionInventoryResponseVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.CustomerCommissionResponseVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.CustomerDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.CustomerListResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

public interface CustomerService {

	void invitingCustomer(CustomerRequestVo request);

	void invitingCustomerAgain(CustomerRequestVo request);

	Integer myInvitingCustomerCount(HttpBaseRequest request);

	CustomerListResponseVo getCustomerListByAdminId(CustomerQueryRequestVo request);

	CustomerDetailResponseVo getCustomerDetailByCustomerId(CustomerDetailRequestVo request);

	CustomerCommissionResponseVo getCommission(HttpBaseRequest request);

	List<CommissionInventoryResponseVo> commissionInventory(HttpBaseRequest request);

}
