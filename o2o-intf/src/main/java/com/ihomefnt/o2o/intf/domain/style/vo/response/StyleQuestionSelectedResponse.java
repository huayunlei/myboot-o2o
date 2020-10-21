package com.ihomefnt.o2o.intf.domain.style.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author huayunlei
 * @created 2018年11月22日 上午8:53:46
 * @desc 已选风格问题记录
 */
@ApiModel("已选问题答案查询结果")
@Data
public class StyleQuestionSelectedResponse {

	@ApiModelProperty("主键")
	private Integer id;//主键
	
	@ApiModelProperty("问题编号")
	private String code;//问题编号
	
	@ApiModelProperty("问题（精简描述）")
	private String questionBrief;//问题（精简描述）
	
	@ApiModelProperty("问题（详细描述）")
	private String questionDetail;//问题（详细描述）
	
	@ApiModelProperty("父主键")
	private Integer pid;//父主键
	
	@ApiModelProperty("数字顺序")
	private Integer sortBy;//数字顺序
	
	@ApiModelProperty("步骤")
	private Integer step;//步骤
	
	@ApiModelProperty("创建时间")
	private Date createTime;//创建时间
	
	@ApiModelProperty("删除标志：0未删除  1已删除")
	private Integer deleteFlag;//删除标志：0未删除  1已删除
	
	@ApiModelProperty("选择类型  0单选，1多选")
	private Integer checkType;//选择类型  0单选，1多选
	
	@ApiModelProperty("备注")
	private String remark;// 备注
	
	@ApiModelProperty("父答案主键")
	private Integer pAnwserId;//父答案主键
	
	@ApiModelProperty("是否自填答案 0选择答案  1自填答案")
	private Integer isFillSlef;//是否自填答案 0选择答案  1自填答案
	
	@ApiModelProperty("答案集合")
	private List<StyleAnwserSelectedResponse> anwserList;

	@ApiModelProperty("问题描述")
	private String  describe;// 问题描述

	@ApiModelProperty("任务状态")
	private Integer taskStatus;

	@ApiModelProperty("任务状态描述")
	private String taskStatusStr;
	
}
