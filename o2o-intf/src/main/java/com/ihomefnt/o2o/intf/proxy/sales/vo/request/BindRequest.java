package com.ihomefnt.o2o.intf.proxy.sales.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

/**
 * Created by hvk687 on 10/21/15.
 */
public class BindRequest extends HttpBaseRequest {
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
