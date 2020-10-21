package com.ihomefnt.o2o.intf.domain.agent.vo.response;

import lombok.Data;

@Data
public class BankCheckResponse {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 校验结果
     */
    private Boolean result;

    /**
     * 校验信息
     */
    private String msg;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     *
     */
    private Boolean creditCard;
}
