/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年7月11日
 * Description:CompanyTableInfo.java 
 */
package com.ihomefnt.o2o.intf.domain.lechange.vo.response;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhang
 */
@Data
public class CompanyTableInfo {

	private String companyName;

	private int onLineCount = 0;

	private int totalCount = 0;
	
	private BigDecimal onLinePer = new BigDecimal(0);


	public BigDecimal getOnLinePer() {
		if (onLineCount == 0 || totalCount == 0) {
			onLinePer = new BigDecimal(0);
		} else {
			
			onLinePer =new BigDecimal(onLineCount).multiply(new BigDecimal(100).divide(new BigDecimal(totalCount),4,RoundingMode.HALF_UP)).setScale(2,RoundingMode.HALF_UP)	;	
		}
		return onLinePer;
	}
}
