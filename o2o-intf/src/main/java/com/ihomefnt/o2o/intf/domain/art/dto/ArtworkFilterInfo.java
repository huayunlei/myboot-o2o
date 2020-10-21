package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

@Data
public class ArtworkFilterInfo {
	
	/**
	 * 筛选项id
	 */
	private int filterId;
	
	/**
	 * 筛选项名称
	 */
	private String filterName;
}
