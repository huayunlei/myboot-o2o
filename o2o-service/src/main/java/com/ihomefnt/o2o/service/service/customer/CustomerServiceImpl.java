package com.ihomefnt.o2o.service.service.customer;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.dao.customer.CustomerDao;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.customer.doo.CommissionInventoryDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerDetailDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerDo;
import com.ihomefnt.o2o.intf.domain.customer.doo.CustomerItemDo;
import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerQueryRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.request.CustomerRequestVo;
import com.ihomefnt.o2o.intf.domain.customer.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.customer.CustomerConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.customer.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	UserProxy userProxy;
	@Autowired
	private CustomerDao commissionDao;
	
	@Override
	public void invitingCustomer(CustomerRequestVo request) {
		// 前台参数判断是否为空
		if (request == null || StringUtils.isBlank(request.getName())
				|| StringUtils.isBlank(request.getMobile())
				|| StringUtils.isBlank(request.getCustomerBuilding())) {
			throw new BusinessException(CustomerConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}
		// 前台参数长度判断是否合法
		if (request.getMobile().trim().length() != 11 || request.getCustomerBuilding().length() > 200) {
			throw new BusinessException(CustomerConstant.MESSAGE_PARAMETERS_DATA_LENGTH);
		}
		// 判断用户是否登陆
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null) {
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}
		
		String mobile = request.getMobile().trim();
		String adviserMobile=userDto.getMobile();
		if(StringUtils.isNotBlank(adviserMobile) && adviserMobile.equals(mobile)){
			throw new BusinessException(CustomerConstant.MSG_SAME);
		}
		// 判断用户是否被邀请过：已邀请true,未邀请false
		boolean invited = this.getInvited(mobile);
		if (invited) {
			throw new BusinessException(CustomerConstant.MSG_INVITED);
		}
		// 判断当天用户是否邀请已经超过20人：超过true,未超过false
		Long adviser = userDto.getId().longValue(); // 置家顾问id
		String adviserName = userDto.getUsername();// 置家顾问中文名
		boolean exceeded = this.getExceeded(adviser);
		if (exceeded) {
			throw new BusinessException(CustomerConstant.MSG_EXCEED);
		}
		request.setAdviser(adviser);
		request.setAdviserName(adviserName);
		// 邀请用户 ：成功true,失败false
		CustomerDo customerDo = ModelMapperUtil.strictMap(request, CustomerDo.class);
		boolean result = this.invitingCustomer(customerDo);
		if (!result) {
			throw new BusinessException(CustomerConstant.MSG_FAILURE);
		}
	}

	@Override
	public void invitingCustomerAgain(CustomerRequestVo request) {
		// 前台参数判断是否为空
		if (request == null ||  StringUtils.isBlank(request.getMobile())) {
			throw new BusinessException(CustomerConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		// 判断用户是否登陆
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null) {
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}
		
		String mobile = request.getMobile().trim();
		// 判断用户是否被邀请过：已邀请true,未邀请false
		boolean invited = this.getInvited(mobile);
		if (invited) {
			throw new BusinessException(CustomerConstant.MSG_INVITED);
		}
		// 判断当天用户是否邀请已经超过20人：超过true,未超过false
		Long adviser = userDto.getId().longValue(); // 置家顾问id
		String adviserName = userDto.getUsername();// 置家顾问中文名
		boolean exceeded = this.getExceeded(adviser);
		if (exceeded) {
			throw new BusinessException(CustomerConstant.MSG_EXCEED);
		}
		request.setAdviser(adviser);
		request.setAdviserName(adviserName);
		// 邀请用户 ：成功true,失败false
		CustomerDo customerDo = ModelMapperUtil.strictMap(request, CustomerDo.class);
		boolean result = this.invitingCustomerAgain(customerDo);
		if (!result) {
			throw new BusinessException(CustomerConstant.MSG_FAILURE);
		}
	}
	
	private boolean getInvited(String mobile) {
		// 判断boss用户跟进是否有记录t_customer
		int customerCount = this.commissionDao.getCustomerCountByMobile(mobile);
		if (customerCount > 0) {
			LOG.info("getInvited return true customerCount>0");
			return true;
		}
		/**
		 * 判断订单是否有记录 订单包含: 软订单 t_order 硬装订单 t_hardorder
		 * 存放是customerId,如果t_customer没有数据,那么t_hardorder肯定没有数据,故不用判断 全品家订单
		 * t_family_order
		 */
		// 判断软订单 t_order是否有记录
		int orderCount = commissionDao.getOrderCountByMobile(mobile);
		if (orderCount > 0) {
			LOG.info("getInvited return true orderCount>0");
			return true;
		}
		// 判断app用户是否已经邀请过
		int appCustomerCount = commissionDao.getAppCustomerCountByMobile(mobile);
		if (appCustomerCount > 0) {
			LOG.info("getInvited return true appCustomerCount>0");
			return true;
		}
		// 全品家订单 t_family_order是否有记录
		int familyOrderCount = commissionDao.getFamilyOrderCountByMobile(mobile);
		if (familyOrderCount > 0) {
			LOG.info("getInvited return true familyOrderCount>0");
			return true;
		}
		LOG.info("getInvited return false");
		return false;
	}
	
	private boolean getExceeded(long adviserId) {
		// 判断app用户是否已经邀请过
		int exceededCount = this.commissionDao.getExceeded(adviserId);
		if (exceededCount >= 20) {
			LOG.info("getExceeded return true exceededCount>=20");
			return true;
		}
		LOG.info("getExceeded return false exceededCount <20");
		return false;
	}

	private boolean invitingCustomer(CustomerDo request) {
		int count = this.commissionDao.invitingCustomer(request);
		if (count == 1) {
			LOG.info("invitingCustomer return true ");
			return true;
		}
		LOG.info("invitingCustomer return false ");
		return false;
	}
	
	private boolean invitingCustomerAgain(CustomerDo request) {
		CustomerDetailDo detail =this.commissionDao.getCustomerByMobile(request.getMobile());
		request.setGender(detail.getGender());
		request.setName(detail.getName());
		request.setCustomerBuilding(detail.getCustomerBuilding());
		request.setRemark(detail.getRemark());
		int count = this.commissionDao.invitingCustomer(request);
		if (count == 1) {
			LOG.info("invitingCustomer return true ");
			return true;
		}
		LOG.info("invitingCustomer return false ");
		return false;
	}	

	@Override
	public Integer myInvitingCustomerCount(HttpBaseRequest request) {
		// 前台参数判断是否为空
		if (request == null) {
			throw new BusinessException(CustomerConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		// 判断用户是否登陆
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null) {
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}
		
		Long adviser = userDto.getId().longValue(); // 置家顾问id
		return commissionDao.getCountByAdviser(adviser);
	}

	@Override
	public CustomerListResponseVo getCustomerListByAdminId(CustomerQueryRequestVo request) {
		// 前台参数判断是否为空
		if (request == null) {
			throw new BusinessException(CustomerConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		// 判断用户是否登陆
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null) {
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}
				
		Long adviser = userDto.getId().longValue(); // 置家顾问id
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("adviser", adviser);
         //0:全部 1:已邀请 2:已到店 3:交易中 4:已结佣 5:到店过期 6:交易过期
        if(request.getCustomerType()>0&&request.getCustomerType()<=6){
        	 paramMap.put("status", request.getCustomerType());
        }
		List<CustomerItemDo> list = commissionDao.getCustomerListByAdviser(paramMap);
		if (CollectionUtils.isEmpty(list)) {
			throw new BusinessException(CustomerConstant.MESSAGE_RESULT_DATA_EMPTY);
		}
		CustomerListResponseVo response = new CustomerListResponseVo();
		List<CustomerItemResponseVo> responseItem = ModelMapperUtil.strictMapList(list, CustomerItemResponseVo.class);
		response.setCustomerList(responseItem);
		return response;
	}

	@Override
	public CustomerDetailResponseVo getCustomerDetailByCustomerId(CustomerDetailRequestVo request) {
		// 前台参数判断是否为空
		if (request == null || StringUtils.isBlank(request.getAccessToken())||request.getCustomerId()==null) {
			throw new BusinessException(CustomerConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}
		Long customerId =request.getCustomerId();//客户ID
		CustomerDetailDo detail = commissionDao.getCustomerByPK(customerId);
		if (detail==null) {
			throw new BusinessException(CustomerConstant.MESSAGE_RESULT_DATA_EMPTY);
		}
		int customerType=detail.getCustomerType();//1:已邀请 2:已到店 3:交易中 4:已结佣 5:到店过期 6:交易过期
		if(customerType==1){
			long daysRemaining=(System.currentTimeMillis()-detail.getAppointmentTimestamp().getTime())/(24* 60 * 60 * 1000L);
			detail.setDaysRemaining(7-(int)daysRemaining);
		}
		if(customerType==2){
			long daysRemaining=(System.currentTimeMillis()-detail.getAppointmentTimestamp().getTime())/(24* 60 * 60 * 1000L);
			detail.setDaysRemaining(30-(int)daysRemaining);								
		}
		
		CustomerDetailResponseVo response = ModelMapperUtil.strictMap(detail, CustomerDetailResponseVo.class);
		return response;
	}

	@Override
	public CustomerCommissionResponseVo getCommission(HttpBaseRequest request) {
		// 前台参数判断是否为空
		if (request == null) {
			throw new BusinessException(CustomerConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		// 判断用户是否登陆
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null) {
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}

		Long adviser = userDto.getId().longValue(); // 置家顾问id		
		int store = commissionDao.getStoreCountByAdviser(adviser)*50;// 到店佣金
		int order =(int)(commissionDao.getOrderPriceTotalByAdviser(adviser)*0.01);// 交易佣金
		Integer total = order+store;// 佣金总额
		CustomerCommissionResponseVo commission = new CustomerCommissionResponseVo();
		commission.setOrder(order);
		commission.setStore(store);
		commission.setTotal(total);
		return commission;
	}

	@Override
	public List<CommissionInventoryResponseVo> commissionInventory(HttpBaseRequest request) {
		// 前台参数判断是否为空
		if (request == null) {
			throw new BusinessException(CustomerConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		// 判断用户是否登陆
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null) {
			throw new BusinessException(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}
				
		Long adviser = userDto.getId().longValue(); // 置家顾问id
		List<CommissionInventoryDo> list = commissionDao.getCommissionInventoryListByAdviser(adviser);
		if (CollectionUtils.isEmpty(list)) {
			throw new BusinessException(CustomerConstant.MESSAGE_RESULT_DATA_EMPTY);
		}
		
		List<CommissionInventoryResponseVo> response = ModelMapperUtil.strictMapList(list, CommissionInventoryResponseVo.class);
		return response;
	}

}
