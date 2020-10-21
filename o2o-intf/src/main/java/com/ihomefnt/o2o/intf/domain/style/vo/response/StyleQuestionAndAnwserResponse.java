package com.ihomefnt.o2o.intf.domain.style.vo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class StyleQuestionAndAnwserResponse {

	private Integer id;//主键
	
	private String code;//问题编号
	
	private String questionBrief;//问题（精简描述）
	
	private String questionDetail;//问题（详细描述）
	
	private Integer pid;//父主键
	
	private Integer sortBy;//数字顺序
	
	private Integer step;//步骤
	
	private Date createTime;//创建时间
	
	private Integer deleteFlag;//删除标志：0未删除  1已删除
	
	private List<StyleAnwserResponse> anwserList;
	
	private Integer checkType;//选择类型  0单选，1多选
	
	private String remark;// 备注
	
	private Integer pAnwserId;//父答案主键
	
	private Integer isFillSlef;//是否自填答案 0选择答案  1自填答案
	
	private String  describe;// 问题描述
	
}
