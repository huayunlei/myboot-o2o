/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月22日
 * Description:SmsProxy.java
 */
package com.ihomefnt.o2o.intf.proxy.sms;

import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import com.ihomefnt.o2o.intf.domain.sms.dto.SendSmsCodeParamVo;

/**
 * @author zhang
 */
public interface SmsProxy {

    /**
     * 检验验证码
     *
     * @param param
     * @return true:验证通过,false:验证失败
     */
    boolean checkSmsCode(CheckSmsCodeParamVo param);

    /**
     * 发送验证码
     *
     * @param param
     * @return 1成功 0失败 2 手机号码发送次数达到上限 3 IP发送次数达到上限 4 60秒内已发送 ;其他 都是失败
     */
    int sendSmsCode(SendSmsCodeParamVo param);

}
