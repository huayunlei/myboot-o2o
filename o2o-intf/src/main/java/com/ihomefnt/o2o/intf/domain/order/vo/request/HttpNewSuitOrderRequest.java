package com.ihomefnt.o2o.intf.domain.order.vo.request;

import lombok.Data;

@Data
public class HttpNewSuitOrderRequest {
    private String suitpara;
    private String receiveName;
    private String receivePhone;
    private String receiveAddress;
}
