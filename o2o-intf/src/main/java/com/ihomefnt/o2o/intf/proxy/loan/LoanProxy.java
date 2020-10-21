package com.ihomefnt.o2o.intf.proxy.loan;

import com.ihomefnt.common.api.ResponseVo;

import com.ihomefnt.o2o.intf.domain.loan.dto.LoanInfoDto;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanMainInfoDto;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanRateDto;
import com.ihomefnt.o2o.intf.domain.loan.vo.request.CreateLoanRequestVo;

import java.util.List;
import java.util.Map;


/**
 * @author xiamingyu
 */
public interface LoanProxy {

    /**
     * 银行和利率查询
     *
     * @param
     * @return
     */
    List<LoanRateDto> queryAllBankLoanRate();

    /**
     * 根据订单号查询银行和利率
     * @param params
     * @return
     */
    List<LoanRateDto> getLoanRateInfoByOrderId(Map<String, Object> params);

    /**
     * 创建贷款
     *
     * @param paramMap
     * @return
     */
    ResponseVo<Integer> createLoan(CreateLoanRequestVo request);

    /**
     * 取消贷款
     *
     * @param paramMap
     * @return
     */
    boolean cancelLoan(Map<String, Object> paramMap);

    /**
     * 根据订单编号查询贷款信息
     *
     * @param orderNum
     * @return
     */
    List<LoanMainInfoDto> queryLoanInfoList(Long orderNum);


    /**
     * 根据贷款id查询贷款详情
     *
     * @param loanId
     * @return
     */
    LoanInfoDto queryLoanInfoById(Long loanId);

}
