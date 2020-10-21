package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by hvk687 on 11/2/15.
 */
@Data
public class SMSSetPassRequestVo extends HttpBaseRequest{
    private String mobile;
    private String password;
    private Integer login;//0:no;1:need not login
    private int messageType;//1发送消息  0不发送（默认不发送）
}
