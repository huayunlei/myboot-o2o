package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.List;

/**
 * 城市套系集合
 * @author ZHAO
 */
@Data
public class CityHardListReaponseVo {
	private List<CityHardVo> hardList;//套系
	
	private List<CityHardVo> cityList;//城市

}
