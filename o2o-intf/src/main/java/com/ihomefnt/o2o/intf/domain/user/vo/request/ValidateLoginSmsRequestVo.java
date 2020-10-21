package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by hvk687 on 11/2/15.
 */
@Data
public class ValidateLoginSmsRequestVo extends HttpBaseRequest{
    private String mobile;
    private String sms;
    private Integer login; //0: no 1: yes  
}
