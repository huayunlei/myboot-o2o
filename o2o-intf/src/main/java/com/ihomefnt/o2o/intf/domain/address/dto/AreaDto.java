package com.ihomefnt.o2o.intf.domain.address.dto;

import lombok.Data;

@Data
public class AreaDto {

	private Long areaId;
	private Long parentId;
	private String areaName;
	private Integer level;
}
