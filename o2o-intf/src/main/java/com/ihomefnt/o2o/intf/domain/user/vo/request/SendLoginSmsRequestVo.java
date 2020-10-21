package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by hvk687 on 11/2/15.
 */
@Data
public class SendLoginSmsRequestVo extends HttpBaseRequest{
    private String mobile;
    private int smsType = 2;    //1 注册  2 登录  3 重置;   因为旧版本没有短信验证码类型 设默认为2
    private String ip;
}
