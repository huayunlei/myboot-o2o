package com.ihomefnt.o2o.intf.domain.art.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 价格区间类
 * @author Charl
 */
@Data
@NoArgsConstructor
public class FieldInfo {
	
	/**
	 * 区域id
	 */
	@JsonProperty(value="filterId")
	private int fieldId;
	
	/**
	 * 区域名称
	 */
	@JsonProperty(value="filterName")
	private String fieldName;
	
	/**
	 * 区域开始
	 */
	private Integer start;
	
	/**
	 * 区域结束
	 */
	private Integer end;

	public FieldInfo(int fieldId, String fieldName, Integer start, Integer end) {
		super();
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.start = start;
		this.end = end;
	}



}
