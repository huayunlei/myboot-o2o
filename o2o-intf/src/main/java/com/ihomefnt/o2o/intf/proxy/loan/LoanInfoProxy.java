package com.ihomefnt.o2o.intf.proxy.loan;

import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinLoanInfoResponseVo;

import java.util.List;
import java.util.Map;

/**
 * 线上贷款信息
 * @author ZHAO
 */
public interface LoanInfoProxy {
	/**
	 * 新增贷款记录
	 * @param paramMap
	 * @return
	 */
	Long createLoanInfo(Map<String, Object> paramMap);
	
	/**
	 * 根据订单编号查询贷款信息
	 * @param orderNum
	 * @return
	 */
	List<AladdinLoanInfoResponseVo> queryLoanInfo(Integer orderNum);
}
