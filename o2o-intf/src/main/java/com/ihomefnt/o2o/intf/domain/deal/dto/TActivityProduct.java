package com.ihomefnt.o2o.intf.domain.deal.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by hvk687 on 9/23/15.
 */
@Data
public class TActivityProduct {
    private Integer count;
    private Integer slot;
    private Timestamp dateBegin;
    private Timestamp dateEnd;
    private Double price;
    private Long prdId;
    private String name;
    private String image;
    private Long actId;
    private Double origPrice;
    private Long actPrdKey;

}
