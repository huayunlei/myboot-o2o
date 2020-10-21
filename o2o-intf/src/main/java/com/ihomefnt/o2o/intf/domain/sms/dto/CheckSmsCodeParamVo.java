package com.ihomefnt.o2o.intf.domain.sms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CheckSmsCodeParamVo {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 类型 1:注册  2:登陆 3:重置
     */
    private Integer type;

    /**
     * 短信验证码
     */
    private String smsCode;


    public CheckSmsCodeParamVo(String mobile, Integer type, String smsCode) {
        this.mobile = mobile;
        this.type = type;
        this.smsCode = smsCode;
    }
}
