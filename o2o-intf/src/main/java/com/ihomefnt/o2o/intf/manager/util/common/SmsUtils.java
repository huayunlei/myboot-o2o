package com.ihomefnt.o2o.intf.manager.util.common;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;

/**
 * @author liyonggang
 * @create 2020-01-10 4:55 下午
 */
public class SmsUtils {

    public static <T> HttpBaseResponse<T> handlerSmsSendResult(int resultCode) {
        if (resultCode == 2 || resultCode == 3) {
            return HttpBaseResponse.fail(HttpResponseCode.SMS_SEND_LIMIT, "今日发送次数已达到上限");
        } else if (resultCode == 4) {
            return HttpBaseResponse.fail(HttpResponseCode.SMS_SEND_LIMIT, "60秒内只能发送一次");
        } else if (resultCode == -1) {
            return HttpBaseResponse.fail(HttpResponseCode.SMS_SEND_MOBILE_FAILED, "请输入正确的手机号码");
        } else {
            return HttpBaseResponse.success();
        }
    }
}
