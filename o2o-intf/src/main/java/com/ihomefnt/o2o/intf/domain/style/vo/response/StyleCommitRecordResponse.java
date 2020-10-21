package com.ihomefnt.o2o.intf.domain.style.vo.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huayunlei
 * @created 2018年11月26日 下午8:16:07
 * @desc 
 */
@ApiModel("用户风格提交记录结果")
@Data
public class StyleCommitRecordResponse {

	@ApiModelProperty("主键")
	private Integer id;//主键
	
	@ApiModelProperty("订单编号")
	private Integer orderNum;
	
	@ApiModelProperty("用户ID")
	private Integer userId;
	
	@ApiModelProperty("创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	
	@ApiModelProperty("删除标志：0未删除  1已删除")
	private Integer deleteFlag;//删除标志：0未删除  1已删除

	@ApiModelProperty("任务id")
	private Integer taskId;
	
	@ApiModelProperty("风格问题")
	private List<StyleQuestionSelectedResponse> selectedQuestionList;
}
