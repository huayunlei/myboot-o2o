package com.ihomefnt.o2o.intf.domain.order.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPayInfoForAlipayResponseVo {
    private String orderNumber;
    private String payInfo;
}
