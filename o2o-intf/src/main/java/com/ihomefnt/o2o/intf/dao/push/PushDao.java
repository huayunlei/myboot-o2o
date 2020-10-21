package com.ihomefnt.o2o.intf.dao.push;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.push.dto.ExtrasDto;
import com.ihomefnt.o2o.intf.domain.push.dto.JpushUpdateRequestVo;
import com.ihomefnt.o2o.intf.domain.push.dto.PushPlatformCenterInfoDto;
import com.ihomefnt.o2o.intf.domain.push.dto.PushTempletEntityDto;
import com.ihomefnt.o2o.intf.domain.push.dto.PushUserCenterInfoDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

public interface PushDao {
	
	/**
	 * 将用户消息修改为已送达
	 */
	void updatePushList(JpushUpdateRequestVo request);
	
	/**
	 * 查询用户推送的消息
	 */
	List<ExtrasDto> queryPushList(HttpBaseRequest request);

	/**
	 * 插入用户消息
	 * @param list
	 */
	void insertPushList(List<PushUserCenterInfoDto> list);

	/**
	 * 插入群推消息
	 */
	void insertPushPlatformCenterInfo(PushPlatformCenterInfoDto center);

	/**
	 * 根据消息类型来获取模板内容
	 * 消息类型:13艾积分到账,14硬装施工报告,15艺术品订单发货,16艾商城通知
	 */
	PushTempletEntityDto getTempletContentByType(Integer type);

}
