package com.ihomefnt.o2o.intf.domain.maintain.vo.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 报修详情
 * @author ZHAO
 */
@Data
public class MaintainTaskDetailResponseVo {
	private Integer taskId;//任务id
	
	private String serviceMobile;//客服电话
	
	private Integer taskStatus;//任务状态
	
	private String taskStatusDesc;//任务状态描述

	private String nextTaskStatusDesc;//下一阶段任务状态描述

	private String praiseDesc;//文案描述
	
	private String maintainTime;//报修时间 2018-03-15 08:18:08
	
	private String linkMan;//联系人
	
	private String linkMobile;//联系手机

	private String hideLinkMobile;//联系手机
	
	private String maintainAddress;//维修地址
	
	private String questionDesc;//问题描述
	
	private List<String> maintainUrls;//维修图片
	
	private String subscribeTime;//预约维修日期2018-03-15
	
	private String cancelMan;//取消人
	
	private String cancelDesc;//取消原因
	
	private Integer orderId;//订单号

	public MaintainTaskDetailResponseVo() {
		this.taskId = 0;
		this.serviceMobile = "";
		this.taskStatus = -1;
		this.taskStatusDesc = "";
		this.nextTaskStatusDesc = "";
		this.praiseDesc = "";
		this.maintainTime = "";
		this.linkMan = "";
		this.linkMobile = "";
		this.hideLinkMobile = "";
		this.maintainAddress = "";
		this.questionDesc = "";
		this.maintainUrls = new ArrayList<>();
		this.subscribeTime = "";
		this.cancelMan = "";
		this.cancelDesc = "";
		this.orderId = -1;
	}

}
