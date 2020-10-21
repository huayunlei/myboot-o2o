package com.ihomefnt.o2o.api.controller.customer;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerQueryRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.CommissionInventoryResponseVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.CustomerCommissionResponseVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.CustomerDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.CustomerListResponseVo;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.customer.CustomerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@Api(tags = "客户API",hidden = true)
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 邀请客户（新增客户）
	 * 
	 */
	@PostMapping(value = "/invitingCustomer")
	public HttpBaseResponse<Void> invitingCustomer(@Json CustomerRequestVo request) {
		customerService.invitingCustomer(request);
		return HttpBaseResponse.success(null);
	}
	
	/**
	 * 邀请客户（再次邀请客户）
	 *
	 */
	@PostMapping(value = "/invitingCustomerAgain")
	public HttpBaseResponse<Void> invitingCustomerAgain(@Json CustomerRequestVo request) {
		customerService.invitingCustomerAgain(request);
		return HttpBaseResponse.success(null);
	}
	
	/**
	 * 我的客户数量
	 * 
	 * @param request
	 * @return
	 * @author Ivan
	 * @date 2016年5月19日 上午9:55:47
	 */
	@PostMapping(value = "/count")
	public HttpBaseResponse<Integer> count(@Json HttpBaseRequest request) {
		Integer obj = customerService.myInvitingCustomerCount(request);
		return HttpBaseResponse.success(obj);
	}
		
	/**
	 * 
	 * 获取该顾问下所有的用户
	 */
	@PostMapping(value = "/list")
	public HttpBaseResponse<CustomerListResponseVo> getCustomerListByAdminId(@Json CustomerQueryRequestVo request) {
		CustomerListResponseVo obj = customerService.getCustomerListByAdminId(request);
		return HttpBaseResponse.success(obj);
	}	
	
	/**
	 * 
	 * 根据客户id查询到客户详情
	 * 
	 */
	@PostMapping(value = "/detail")
	public HttpBaseResponse<CustomerDetailResponseVo> getCustomerDetailByCustomerId (@Json CustomerDetailRequestVo request){
		CustomerDetailResponseVo obj = customerService.getCustomerDetailByCustomerId(request);
		return HttpBaseResponse.success(obj);
	}
	
	/**
	 * 我的佣金
	 * 
	 * @param request
	 * @return
	 * @author Ivan
	 * @date 2016年5月19日 上午9:55:47
	 */
	@PostMapping(value = "/commission")
	public HttpBaseResponse<CustomerCommissionResponseVo> commission(@Json HttpBaseRequest request) {
		CustomerCommissionResponseVo obj = customerService.getCommission(request);
		return HttpBaseResponse.success(obj);
	}
	
	/**
	 * 佣金清单
	 * @param request
	 * @return
	 * @author Ivan
	 * @date 2016年5月19日 上午9:55:47
	 */
	@PostMapping(value = "/commissionInventory")
	public HttpBaseResponse<List<CommissionInventoryResponseVo>> commissionInventory(@Json HttpBaseRequest request) {
		List<CommissionInventoryResponseVo> obj = customerService.commissionInventory(request);
		return HttpBaseResponse.success(obj);
	}	
	
}