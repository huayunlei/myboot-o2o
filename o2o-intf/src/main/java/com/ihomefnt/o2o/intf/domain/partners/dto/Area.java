package com.ihomefnt.o2o.intf.domain.partners.dto;

import lombok.Data;

import java.util.List;
@Data
public class Area {

	private Integer areaId;
	private String areaName;
	private List<Building> buildingList;
}
