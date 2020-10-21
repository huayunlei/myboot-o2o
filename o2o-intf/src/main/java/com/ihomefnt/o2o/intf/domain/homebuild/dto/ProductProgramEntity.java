package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;

/**
 * 产品方案
 * @author ZHAO
 */
@Data
public class ProductProgramEntity {
	private Integer programId;//方案ID
	
	private String name;//方案名称
	
	private String headImgUrl;//头图
}
