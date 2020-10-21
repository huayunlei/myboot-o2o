package com.ihomefnt.o2o.service.service.log;

import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.log.vo.request.LogRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.log.LogEnum;
import com.ihomefnt.o2o.intf.proxy.user.LogProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {
	
	@Autowired
	private UserProxy userProxy;
	@Autowired
	private LogProxy logProxy;

	@Override
	public void addOperationLog(LogRequestVo request) {
    	Integer visitType = request.getVisitType() != null ? request.getVisitType() : 0;
    	// 增加日志
		HttpUserInfoRequest userDto = request.getUserInfo();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceToken", request.getDeviceToken());
		params.put("mobile", userDto.getMobile());
		params.put("visitType", visitType);
		params.put("action", LogEnum.getMsg(visitType));
		params.put("userId", userDto.getId());
		params.put("appVersion", request.getAppVersion());
		params.put("osType", request.getOsType());
		params.put("pValue", request.getParterValue());
		params.put("cityCode", request.getCityCode());
		params.put("businessCode", request.getBusinessCode());
		params.put("commonValue", request.getCommonValue());
		logProxy.addLog(params);
	}

	@Override
	public void addLog(LogRequestVo request) {
		Integer userId = 0;
    	Integer visitType = request.getVisitType() != null ? request.getVisitType() : 0;
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto != null) {
			userId = userDto.getId();
		}
		// 增加日志
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceToken", request.getDeviceToken());
		params.put("mobile", request.getMobileNum());
		params.put("visitType", visitType);
		params.put("action", LogEnum.getMsg(visitType));
		params.put("userId", userId);
		params.put("appVersion", request.getAppVersion());
		params.put("osType", request.getOsType());
		params.put("pValue", request.getParterValue());
		params.put("cityCode", request.getCityCode());
		params.put("businessCode", request.getBusinessCode());
		logProxy.addLog(params);
	}

}
