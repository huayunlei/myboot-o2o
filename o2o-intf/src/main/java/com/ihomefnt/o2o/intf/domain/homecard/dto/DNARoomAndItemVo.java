package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.List;

/**
 * 家具清单集合
 * @author ZHAO
 */
@Data
public class DNARoomAndItemVo {
	private String roomUseName;// 空间类型名称,
	
	private List<DNARoomItemVo> dnaRoomItemList;// 空间家具清单

}
