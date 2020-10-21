package com.ihomefnt.o2o.intf.domain.bankcard.vo.response;

import lombok.Data;

/**
 * 银行卡信息返回数据
 */
@Data
public class BankCardResponseVo {

    /**
     * "绑定状态，1已绑定 0未绑定
     */
    private Integer state;

    /**
     * 银行名称
     */
    private String bankName;


    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 用户名
     */
    private String name;
}
