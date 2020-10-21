package com.ihomefnt.o2o.intf.service.loan;

import com.ihomefnt.o2o.intf.domain.loan.vo.request.CancelLoanRequestVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.request.CreateLoanRequestVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.LoanBaseDataResponseVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.LoanDetailDataResponseVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.LoanListResponseVo;
import com.ihomefnt.o2o.intf.domain.loan.vo.response.UnpaidMoneyResponseVo;


/**
 * @author xiamingyu
 */
public interface LoanService {


    /**
     * 银行和利率查询
     *
     * @param
     * @return
     */
    LoanBaseDataResponseVo queryAllBankLoanRate();

    /**
     * 根据订单号查询贷款年限及利率
     * @param orderId
     * @return
     */
    LoanBaseDataResponseVo getLoanRateInfoByOrderId(Integer orderId);

    /**
     * 创建贷款
     *
     * @param request
     * @return
     */
    Integer createLoan(CreateLoanRequestVo request);

    /**
     * 取消贷款
     *
     * @param request
     * @return
     */
    boolean cancelLoan(CancelLoanRequestVo request);

    /**
     * 根据订单编号查询贷款信息
     *
     * @param orderNum
     * @return
     */
    LoanListResponseVo queryLoanInfoList(Long orderNum);


    /**
     * 根据贷款id查询贷款详情
     *
     * @param loanId
     * @return
     */
    LoanDetailDataResponseVo queryLoanInfoById(Long loanId);

    /**
     * 查询订单款项简单信息
     *
     * @param orderid
     * @return
     */
    UnpaidMoneyResponseVo getUnpaidMoneyByOrderId(Integer orderid);
}
