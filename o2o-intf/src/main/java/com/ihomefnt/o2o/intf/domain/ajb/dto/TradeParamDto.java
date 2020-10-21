/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: Ivan Shen
 * Date: 2016年8月9日
 * Description:AccountTradeDto.java 
 */
package com.ihomefnt.o2o.intf.domain.ajb.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易信息
 * @author Ivan Shen
 */
@Data
@ApiModel("交易信息")
public class TradeParamDto {
	
	/**
     *会员id
     */
    private Integer userId;
    
    /**
     * 订单编号
     */
    private String orderNum;

    /**
     *现金券金额
     */
    private BigDecimal cashCouponAmount;
    
    /**
     *诚意金金额
     */
    private BigDecimal earnestMoneyAmount;
    
    /**
     *定金金额
     */
    private BigDecimal frontMoneyAmount;
    
    /**
     * 艾积分金额
     */
    private Integer ajbAmount;

    /**
     *备注
     */
    private String remark;
}
