package com.ihomefnt.o2o.intf.proxy.sales.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

/**
 * Created by hvk687 on 10/20/15.
 */
public class CustomerListRequest extends HttpBaseRequest {
    private Integer from;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
}
