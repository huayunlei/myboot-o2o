package com.ihomefnt.o2o.intf.domain.style.vo.response;

import lombok.Data;

import java.util.Map;

/**
 * @author huayunlei
 * @created 2018年11月22日 下午3:38:46
 * @desc 所有问题和答案的map集
 */
@Data
public class StyleQuestionAnwserMapResponse {

	/**
	 * 问题集
	 */
	private Map<String, StyleQuestionDto> styleQuestionMap;
	
	/**
	 * 答案集
	 */
	private Map<String, StyleAnwserDto> styleAnwserMap;

}
