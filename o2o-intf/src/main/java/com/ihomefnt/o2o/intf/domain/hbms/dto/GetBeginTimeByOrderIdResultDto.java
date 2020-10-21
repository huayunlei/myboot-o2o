/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年6月20日
 * Description:GetBeginTimeByOrderIdResultDto.java 
 */
package com.ihomefnt.o2o.intf.domain.hbms.dto;

import lombok.Data;

import java.util.Date;

/**
 * 查询hbms施工信息结果对象
 * 
 * @author chong
 */
@Data
public class GetBeginTimeByOrderIdResultDto {

	// 施工日期
	private Date beginTime;

}
