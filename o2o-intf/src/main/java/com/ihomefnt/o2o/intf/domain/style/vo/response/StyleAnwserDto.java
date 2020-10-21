package com.ihomefnt.o2o.intf.domain.style.vo.response;

import java.util.Date;

/**
 * @author huayunlei
 * @created 2018年11月21日 上午10:41:15
 * @desc 风格答案
 */
public class StyleAnwserDto {

	private Integer id;//主键
	
	private String code;//答案编号
	
	private String anwser;//答案描述
	
	private Integer questionId;//问题主键

	private Date createTime;//创建时间
	
	private Integer deleteFlag;//删除标志：0未删除  1已删除
	
	private String  describe;// 答案描述
}
