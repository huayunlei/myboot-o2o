package com.ihomefnt.o2o.intf.domain.bankcard.vo.response;

import lombok.Data;

/**
 * 银行卡信息返回数据
 */
@Data
public class CheckCardResponseVo {


    /**
     * 卡片类型 2为借记卡 3为信用卡
     */
    private Integer cardType;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 卡号
     */
    private String cardNumber;
}
