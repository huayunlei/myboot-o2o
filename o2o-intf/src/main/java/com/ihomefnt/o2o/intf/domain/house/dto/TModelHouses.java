package com.ihomefnt.o2o.intf.domain.house.dto;

import lombok.Data;

@Data
public class TModelHouses {
	private Long id;
	private String mobile;
	private String name;
	private String styleName;
	private String material;
	private String budget;
	private String address;
	private Integer status;
}
