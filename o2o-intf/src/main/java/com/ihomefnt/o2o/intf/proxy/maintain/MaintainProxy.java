/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月26日
 * Description:IMaintainProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.maintain;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskCommentDto;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskCreateUpdateDto;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskDetailDto;

/**
 * @author zhang
 */
public interface MaintainProxy {

	/**
	 * 提交报修
	 * 
	 * @param vo
	 * @return
	 */
	Integer addTask(TaskCreateUpdateDto vo);
	
	/**
	 * 修改报修
	 * @param vo
	 * @return
	 */
	boolean updateTask(TaskCreateUpdateDto vo);

	/**
	 * 取消任务
	 * @param id
	 * @return
	 */
	boolean cancelTask(Integer id);
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	TaskDetailDto queryDetail(Integer id);
	
	/**
	 * 查询任务记录列表
	 * @param userId
	 * @param statusList
	 * @return
	 */
	List<TaskDetailDto> queryList(Integer userId, List<Integer> statusList, Integer orderId);
	
	/**
	 * 新增评价
	 * @param vo
	 * @return
	 */
	boolean comment(TaskCommentDto vo);
}
