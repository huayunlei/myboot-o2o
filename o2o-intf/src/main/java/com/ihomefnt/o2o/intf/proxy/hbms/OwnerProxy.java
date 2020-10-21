/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年8月21日
 * Description:IOwnerProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.hbms;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.hbms.dto.CommentParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.ConfirmNodeParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetCommentParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetCommentResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetNodeItemsParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetNodeItemsResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetSurveyorProjectNodeDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetUnhandleProjectResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.NeedConfirmItemsDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.OwnerParamDto;

/**
 * @author zhang
 */
public interface OwnerProxy {

	/**
	 * 节点是否满意
	 * 
	 * @param request
	 * @return true:操作成功,false:操作失败
	 */
	boolean confirmNode(ConfirmNodeParamDto request);

	/**
	 * 节点验收项
	 * 
	 * @param request
	 * @return
	 */
	GetNodeItemsResultDto getNodeItems(GetNodeItemsParamDto request);

	/**
	 * 施工点评
	 * 
	 * @param request
	 * @return true:操作成功,false:操作失败
	 */
	boolean comment(CommentParamDto request);

	/**
	 * 获取施工点评
	 * 
	 * @param request
	 * @return
	 */
	GetCommentResultDto getComment(GetCommentParamDto request);

	/**
	 * 获取项目工艺流程
	 * 
	 * @param request
	 * @return
	 */
	List<GetSurveyorProjectNodeDto> getProjectCraft(OwnerParamDto request);

	/**
	 * 项目详情
	 * 
	 * @param request
	 * @return
	 */
	GetUnhandleProjectResultDto getUnhandleProject(OwnerParamDto request);

	/**
	 * 查询用户待确认的自定义项
	 * @param orderId
	 * @return
	 */
	List<NeedConfirmItemsDto> queryNeedConfirmItem(Integer orderId);

	/**
	 * 用户确认工期变更
	 * @param orderId
	 * @return
	 */
	Integer confirmTimeChange(Integer orderId);
}
