package com.ihomefnt.o2o.intf.domain.partners.dto;

import lombok.Data;

@Data
public class TPartners {

	private Long id;
	private String mobile;
	private Integer age;
	private String name;
	private String vocation;//行业
	private Integer areaId;
	private Integer buildingId;
	private Integer status;
}
