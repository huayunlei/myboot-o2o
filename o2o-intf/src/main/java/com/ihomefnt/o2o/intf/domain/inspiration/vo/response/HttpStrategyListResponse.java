package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.Strategy;
import lombok.Data;

import java.util.List;
@Data
public class HttpStrategyListResponse {
	
	private List<Strategy> strategyList;
	private List<Strategy> strategyHomeList;
    private int totalRecords;
    private int totalPages;
}
