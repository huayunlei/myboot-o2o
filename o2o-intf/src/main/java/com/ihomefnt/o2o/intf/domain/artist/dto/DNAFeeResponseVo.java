/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月13日
 * Description:DNAFeeResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class DNAFeeResponseVo {

	private Integer dnaId;// DNA id,
	private String dnaName;// DNA名称,
	private BigDecimal dnaFeeAmount;// DNA版权收益,
	private BigDecimal dnaDesignFee;// DNA设计费（即预付款）
}
