package com.ihomefnt.o2o.intf.domain.deal.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by hvk687 on 9/25/15.
 */
@Data
public class TDealOrder {
    private Long dealOrderId;
    private Long userId;
    private Long actPrdId;
    private Integer status;
    private String orderNo;
    private Timestamp dateEnd;
}
