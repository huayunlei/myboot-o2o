package com.ihomefnt.o2o.service.proxy.ajb;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.Page;
import com.ihomefnt.o2o.intf.domain.ajb.dto.*;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbActivityResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbRecordListResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AccountWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.ajb.AjbProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 艾积分服务代理
 * @see http://192.168.1.31:10000/account-web/account/newAjb/queryBookRecords
 * @author ZHAO
 */
@Service
public class AjbProxyImpl implements AjbProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public AjbBillNoDto ajbActivityRecharge(Integer userId, Integer amount, String remark, Integer activityCode) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("remark", remark);
		param.put("amount", amount);
		param.put("activityCode", activityCode);
		try {
			ResponseVo<AjbBillNoDto> response = strongSercviceCaller.post(AccountWebServiceNameConstants.ACCOUNT_RECHARGE, param,
					new TypeReference<ResponseVo<AjbBillNoDto>>() {
			});
			
			if (null != response && response.isSuccess()) {
				return response.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	//@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public UserAjbRecordDto queryAjbDetailInfoByUserId(Integer userId, Integer pageNo, Integer pageSize) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("pageNo", pageNo);
		param.put("pageSize", pageSize);
		try {
			ResponseVo<UserAjbRecordDto> response = strongSercviceCaller.post(AccountWebServiceNameConstants.QUERY_AJB_DETAIL_INFO_BY_USER_ID, param,
					new TypeReference<ResponseVo<UserAjbRecordDto>>() {
			});
			if (null != response && response.isSuccess() && null != response.getData()) {
				return response.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return new UserAjbRecordDto();
	}


	@Override
	public Page<AccountBookRecordDto> queryBookRecords(AjbSearchDto param) {
		try {
			ResponseVo<List<AccountBookRecordDto>> response = strongSercviceCaller.post(AccountWebServiceNameConstants.QUERY_BOOK_RECORDS, param,
					new TypeReference<ResponseVo<List<AccountBookRecordDto>>>() {
			});
			
			if (null != response && response.isSuccess()) {
				List<AccountBookRecordDto> list = response.getData();
				if(CollectionUtils.isNotEmpty(list)){
					Page<AccountBookRecordDto> page =new Page<AccountBookRecordDto>(); 
					page.setList(list);
					page.setCount(list.size());
					return page;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}


	@Override
	public boolean confirmPay(OrderNumDto param) {
		try {
			ResponseVo<Boolean> response = strongSercviceCaller.post(AccountWebServiceNameConstants.ACCOUNT_CONFIRM_PAY, param,
					new TypeReference<ResponseVo<Boolean>>() {
			});
			
			if (null != response && response.isSuccess()) {
				return response.getData();
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}


	@Override
	public boolean freezePay(TradeParamDto param) {
		try {
			ResponseVo<Boolean> response = strongSercviceCaller.post(AccountWebServiceNameConstants.ACCOUNT_FREEZE_PAY, param,
					new TypeReference<ResponseVo<Boolean>>() {
			});
			
			if (null != response && response.isSuccess()) {
				return response.getData();
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	@Override
	public AjbActivityResponseVo queryAjbActivityByCode(String activityCode) {
		Map<String, Object> param = new HashMap<>();
		param.put("code", activityCode);
		
		try {
			HttpBaseResponse<AjbActivityResponseVo> response = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_AJB_ACTIVITY_BY_CODE, param,
					new TypeReference<HttpBaseResponse<AjbActivityResponseVo>>() {
			});
			
			if (null != response ) {
				return response.getObj();
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}

	@Override
	public boolean addAjbRecord(Integer userId, String orderNum, String activityCode) {
		Map<String, Object> param = new HashMap<>();
		param.put("activityCode", activityCode);
		param.put("orderNum", orderNum);
		param.put("userId", userId);
		
		try {
			HttpBaseResponse<Boolean> response = strongSercviceCaller.post(WcmWebServiceNameConstants.ADD_AJB_RECORD, param,
					new TypeReference<HttpBaseResponse<Boolean>>() {
			});
			
			if (null != response ) {
				return response.getObj();
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}

	@Override
	public AjbRecordListResponseVo queryRecordByCodeAndUserId(Integer userId, String activityCode) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("code", activityCode);
		
		try {
			HttpBaseResponse<AjbRecordListResponseVo> response = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_RECORD_BY_CODE_AND_USER_ID, param,
					new TypeReference<HttpBaseResponse<AjbRecordListResponseVo>>() {
			});
			
			if (null != response ) {
				return response.getObj();
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}


}
