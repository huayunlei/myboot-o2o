package com.ihomefnt.o2o.intf.domain.maintain.dto;

import lombok.Data;

/**
 * 任务评价
 * @author ZHAO
 */
@Data
public class TaskCommentDto {
	private Integer id;//报修单号
	
	private Integer score;//总体满意度
	
	private String content;//评论内容
	
	private Integer serviceScore;//客服人员服务满意度
	
	private Integer repairScore;//维修人员服务满意度
	
	private Integer scheduleScore;// 维修工期满意度
	
	private Integer qualityScore;//维修质量满意度
}
