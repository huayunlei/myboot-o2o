package com.ihomefnt.o2o.intf.domain.maintain.dto;

import lombok.Data;

import java.util.List;

/**
 * 任务详情
 * @author ZHAO
 */
@Data
public class TaskDetailDto {
	private Integer id;//报修单号
	
	private String createTime;//申请时间
	
	private Integer status;//状态：0待分配、1待预约、2处理中、3已处理、4已完成、5已取消
	
	private Integer commentStatus;//评论状态：0未点评，1已点评
	
	private String linkmanName;//联系人姓名
	
	private String linkmanMobile;//联系人手机号
	
	private String address;//报修地址
	
	private String clientDesc;//问题描述
	
	private List<String> pictureUrls;//维修图片
	
	private String cancelDesc;//取消备注
	
	private Integer cancelId;//取消人
	
	private String cancelName;//取消人姓名
	
	private String appointDate;//预约日期
	
	private Integer orderId;//订单号

}
