package com.ihomefnt.o2o.intf.manager.constant.loan;

/**
 * @author xiamingyu
 * @date 2018/6/22
 */

public interface LoanConstant {

    //中国银行Id
    Long BANK_OF_CHINA_ID = 1L;

    //中国银行名称
    String BANK_OF_CHINA_NAME = "中国银行";

    //类型为其他的reasonId
    Integer REASON_ID_OTHER = 2;

    /**
     * LOAN_STATUS_PROCESS(1, "办理中"),
     * LOAN_STATUS_ENTRY(2,"入账中"),
     * LOAN_STATUS_COMPLETED(3, "已入账"),
     * LOAN_STATUS_CANCELED(4, "已取消"),
     * LOAN_STATUS_ENTERTAIN(5, "下款中"),
     * LOAN_STATUS_REJECT(6, "已驳回"),
     */

    //办理中
    Integer STATUS_PROCESS = 1;

    //办理中
    Integer STATUS_ENTRY = 2;

    //办理中
    Integer STATUS_COMPLETED = 3;

    //取消状态
    Integer STATUS_CANCEL = 4;

    //办理中
    Integer STATUS_ENTERTAIN = 5;

    //办理中
    Integer STATUS_REJECT = 6;

}
