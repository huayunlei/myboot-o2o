package com.ihomefnt.o2o.intf.domain.ad.dto;

import lombok.Data;

import java.util.List;

/**
 * 启动页
 * @author ZHAO
 */
@Data
public class AdvertListDto {
	private List<AdvertDto> startPageList;
}
