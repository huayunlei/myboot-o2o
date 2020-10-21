/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月13日
 * Description:DNAFeePayResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import com.ihomefnt.o2o.intf.manager.constant.artist.StatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhang
 */
@Data
public class DNAFeePayResponseVo {

	private Integer id;// 到账记录id
	private Date createTime;// 提交申请时间,
	private String createTimeDesc;// yyyy年MM月dd日 hh:mm

	private Integer status;// 状态:0.待审核1.待付款2.付款中3.已付款4.已驳回,
	private String statusDesc;

	private BigDecimal amount;// 到账金额

	public String getCreateTimeDesc() {
		if (createTime != null) {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
			return dfs.format(createTime);
		}
		return createTimeDesc;
	}

	public String getStatusDesc() {
		if (status != null) {
			return StatusEnum.getMsg(status);
		}
		return statusDesc;
	}
}
