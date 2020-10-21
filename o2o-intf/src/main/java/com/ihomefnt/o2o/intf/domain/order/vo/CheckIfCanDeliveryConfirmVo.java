package com.ihomefnt.o2o.intf.domain.order.vo;

import lombok.Data;

@Data
public class CheckIfCanDeliveryConfirmVo {
    private Boolean checkResult;

    private String msg;

    private String planBeginDateStr;
}
