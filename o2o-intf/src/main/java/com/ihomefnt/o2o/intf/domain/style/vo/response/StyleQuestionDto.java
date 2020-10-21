package com.ihomefnt.o2o.intf.domain.style.vo.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huayunlei
 * @created 2018年11月21日 上午10:38:51
 * @desc 风格问题
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("风格问题对象")
public class StyleQuestionDto {

	private Integer id;//主键
	
	private String code;//问题编号
	
	private String questionBrief;//问题（精简描述）
	
	private String questionDetail;//问题（详细描述）
	
	private Integer pid;//父主键
	
	private Integer sortBy;//数字顺序
	
	private Integer step;//步骤
	
	private Date createTime;//创建时间
	
	private Integer deleteFlag;//删除标志：0未删除  1已删除
	
	private Integer checkType;//选择类型  0单选，1多选
	
	private String remark;// 备注
	
	private Integer pAnwserId;//父答案主键
	
	private Integer isFillSlef;//是否自填答案 0选择答案  1自填答案
	
	private String  describe;// 问题描述
	
}
