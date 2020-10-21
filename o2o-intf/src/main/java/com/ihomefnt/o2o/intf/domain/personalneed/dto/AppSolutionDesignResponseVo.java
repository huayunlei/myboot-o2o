package com.ihomefnt.o2o.intf.domain.personalneed.dto;

import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAProspectPictureVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 个性化需求
 * Author: ZHAO
 * Date: 2018年5月26日
 */
@Data
public class AppSolutionDesignResponseVo {
	private Integer userId;//用户ID
	
	private String userTags;//用户标签
	
	private List<DNAProspectPictureVo> prospectPictureList;//意境图列表
	
	private String budget;//家装预算
	
	private String hardQuality;//硬装质量
	
	private String remark;//备注

	private Integer dnaId;//DNA  id
	
	private String dnaName;//DNA名称

	private String dnaStyle;//DNA风格名称
	
	private String dnaHeadImg;//DNA 首图

	private Integer taskStatus;//任务状态

	private Integer solutionId;//方案Id

	private String taskStatusStr;//任务状态描述

	private String createTime;//提交时间

	@ApiModelProperty("任务id")
	private Integer taskId;
	
	private List<DesignDnaRoomVo> taskDnaRoomList;// DNA空间集合,

	private Integer orderNum;

	private String taskStatusName;
	
}
