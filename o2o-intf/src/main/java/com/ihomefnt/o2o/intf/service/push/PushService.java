package com.ihomefnt.o2o.intf.service.push;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.push.dto.ExtrasDto;
import com.ihomefnt.o2o.intf.domain.push.dto.JpushParamDto;
import com.ihomefnt.o2o.intf.domain.push.dto.JpushUpdateRequestVo;
import com.ihomefnt.o2o.intf.domain.push.dto.PushPlatformCenterInfoDto;
import com.ihomefnt.o2o.intf.domain.push.dto.PushUserCenterInfoDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

public interface PushService {

	/**
	 * 将用户消息修改为已送达
	 */
	void updatePushList(JpushUpdateRequestVo request);
	
	/**
	 * 查询用户推送的消息
	 */
	List<ExtrasDto> queryPushList(HttpBaseRequest request);

	/**
	 * 插入群推消息
	 */
	void insertPushPlatformCenterInfo(PushPlatformCenterInfoDto center);

	/**
	 * 插入用户消息
	 */
	void insertPushList(List<PushUserCenterInfoDto> list);

	/**
	 * 调用公共服务平台推送push
	 */
	Integer sendPushPersonalMessage(JpushParamDto jpushRequest);


}
