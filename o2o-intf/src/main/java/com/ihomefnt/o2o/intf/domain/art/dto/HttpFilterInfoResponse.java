package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.util.List;

@Data
public class HttpFilterInfoResponse {

	/**
	 * 类别选项
	 */
	private List<ArtworkFilterInfo> typeList;
	
	/**
	 * 价格选项
	 */
	private List<FieldInfo> priceList;
	
	/**
	 * 空间选项
	 */
	private List<ArtworkFilterInfo> roomList;
}
