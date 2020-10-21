package com.ihomefnt.o2o.intf.proxy.designdemand;

import com.ihomefnt.o2o.intf.domain.style.vo.request.StyleQuestionAnwserCommitNewRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.response.*;

import java.util.List;
import java.util.Map;

/**
 * @author huayunlei
 * @created 2018年11月22日 上午11:45:37
 * @desc 风格问答服务代理接口
 */
public interface StyleQuestionAnwserProxy {

	/**提交风格问题答案集合
	 * @param questionAnwsers
	 */
	Integer commitStyleQuestionAnwser(StyleQuestionAnwserCommitNewRequest questionAnwsers);

	/**
	 * 查询所有问题答案
	 */
	List<StyleQuestionAnwserStepResponse> queryAllQuestionAnwserList(Integer version);
	
	/**查询所有问题和答案的map集
	 * @return
	 */
	StyleQuestionAnwserMapResponse queryAllQuestionAnwserMap(Integer version);

	/** 根据提交记录Id查询订单已选问题答案详情
	 * @param commitRecordId
	 * @return
	 */
	StyleQuestionSelectedNewResponse queryQuestionAnwserDetail(Integer commitRecordId,Integer orderNum);

	/**查询最新的提交的问题答案详情
	 * @param orderNum
	 * @param userId
	 * @return
	 */
	StyleQuestionSelectedNewResponse queryQuestionAnwserDetailLatest(Integer orderNum, Integer userId);

	/**
	 * @param orderId
	 * @return
	 */
	List<StyleCommitRecordResponse> queryStyleCommitRecordList(Integer orderId);

	List<StyleAnwserResponse> queryAllAnwserList(Map param);
}
