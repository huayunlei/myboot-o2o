package com.ihomefnt.o2o.intf.domain.sms.dto;

import lombok.Data;

@Data
public class SendSmsCodeParamVo {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 类型 1:注册  2:登陆 3:重置
     */
    private Integer type;

    /**
     * 用户的真实IP地址
     */
    private String ip;
}
