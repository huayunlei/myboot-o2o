package com.ihomefnt.o2o.intf.service.sms;

import com.ihomefnt.o2o.intf.domain.sms.dto.CheckSmsCodeParamVo;
import com.ihomefnt.o2o.intf.domain.sms.dto.SendSmsCodeParamVo;

import java.util.Map;

/**
 * Created by shirely_geng on 15-1-15.
 */
public interface SmsService {
    //send sms
    boolean sendSms(String sms, String mobile);

    boolean sendSms(Map<String, String> map);

    boolean sendSmsKey(String code, String mobile, String key);

    boolean sendRushSms(Map<String, String> map);

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
