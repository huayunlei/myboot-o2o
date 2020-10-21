package com.ihomefnt.o2o.intf.domain.customer.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class CommissionRuleDto implements Serializable {

    //楼盘id
    private Long buildingId;

    //佣金比例
    private BigDecimal rate;

    //是否为默认比例
    private Boolean defaultFlag;
    
    //交易类型.取值：1-收款，2-退款 
    private Integer transactionType;
}