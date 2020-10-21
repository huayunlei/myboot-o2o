package com.ihomefnt.o2o.intf.domain.agent.vo.request;

import lombok.Data;

/**
 * 银行校验入参
 */
@Data
public class BankCheckRequest {

    /**
     * 银行卡
     */
    private String bankcard;

    /**
     * 身份证
     */
    private String cardNo;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 模块
     */
    private String module;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 签名
     */
    private String sign;

    /**
     * 用户ID
     */
    private Long userId;


}