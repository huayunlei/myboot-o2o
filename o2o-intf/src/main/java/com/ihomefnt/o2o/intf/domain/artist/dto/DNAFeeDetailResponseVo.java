/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月13日
 * Description:DNAFeeDetailResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhang
 */
@Data
public class DNAFeeDetailResponseVo {

	private Integer dnaId;// DNA id,
	private String dnaName;// DNA名称,

	private Date createTime;// 产生收益时间,
	private String createTimeDesc;// yyyy年MM月dd日 hh:mm

	private BigDecimal amount;// 收益金额

	public String getCreateTimeDesc() {
		if (createTime != null) {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
			return dfs.format(createTime);
		}
		return createTimeDesc;
	}
}
