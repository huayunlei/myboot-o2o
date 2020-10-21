/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月17日
 * Description:DnaVisitListResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class DnaVisitListResponse {
	
	private List<DnaVisitResponse> list;
}
