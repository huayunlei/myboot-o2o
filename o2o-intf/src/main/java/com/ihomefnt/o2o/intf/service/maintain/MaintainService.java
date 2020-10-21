/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月26日
 * Description:MaintainService.java 
 */
package com.ihomefnt.o2o.intf.service.maintain;

import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainJudgeRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainServiceEvaluationRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainTaskDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainTaskRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainAddTaskResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainTaskDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainUserInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;

/**
 * @author zhang
 */
public interface MaintainService {

	/**
	 * 查询报修的用户信息
	 * 
	 * @param request
	 * @return
	 */
	MaintainUserInfoResponseVo getMaintainUserInfo(HttpBaseRequest request);

	/**
	 * 提交报修任务
	 * 
	 * @param request
	 * @return
	 */
	Integer addTask(MaintainTaskRequestVo request);

	/**
	 * 更新报修任务
	 * 
	 * @param request
	 * @return
	 */
	Integer updateTask(MaintainTaskRequestVo request);

	/**
	 * 取消报修任务
	 * 
	 * @param taskId
	 * @return
	 */
	Integer cancelTask(Integer taskId);
	
	/**
	 * 查询报修记录列表
	 * @param request
	 * @return
	 */
	PageModel<MaintainTaskDetailResponseVo> queryMaintainRecordList(MaintainJudgeRequestVo request);

	/**
	 * 查询报修记录详情
	 * @param request
	 * @return
	 */
	MaintainTaskDetailResponseVo queryMaintainRecordDetail(MaintainTaskDetailRequestVo request);
	
	/**
	 * 新增服务评价
	 * @param request
	 * @return
	 */
	Boolean addServiceEvaluation(MaintainServiceEvaluationRequestVo request);
	
	/**
	 * 判断正在处理中的报修记录
	 * @return
	 */
	Boolean judgeInProcess(MaintainJudgeRequestVo request);

	MaintainAddTaskResponseVo maintainAddTask(MaintainTaskRequestVo request);
}
