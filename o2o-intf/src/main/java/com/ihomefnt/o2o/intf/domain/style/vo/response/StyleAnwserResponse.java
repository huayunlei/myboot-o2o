package com.ihomefnt.o2o.intf.domain.style.vo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author huayunlei
 * @created 2018年11月21日 上午10:41:15
 * @desc 风格答案
 */
@Data
public class StyleAnwserResponse {

	private Integer id;//主键
	
	private String code;//答案编号
	
	private String anwser;//答案描述
	
	private Integer questionId;//问题主键

	private Date createTime;//创建时间
	
	private Integer deleteFlag;//删除标志：0未删除  1已删除
	
	private List<StyleQuestionAndAnwserResponse> questionList;
	
	private String  describe;// 答案描述

	private Integer mutexFlag;//多选项互斥标记位 0 不互斥 1互斥
	
}
