package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.util.List;

/**
 * 空间列表
 * @author ZHAO
 */
@Data
public class DNARoomVo {

	private Integer roomId;// 空间id,

	private String roomName; //空间名称

	private String roomPictureUrl;//空间首图

	private Integer roomUseId;// 空间用途id,

	private String roomType;//空间类型
	
	private Integer usedCount;//方案使用次数字段
	
	private List<DNARoomPictureVo> roomPictureList;//空间效果图列表
	
	private String roomDescription;//空间文字描述
	
	private List<String> roomItemBrandList;//空间品牌文案列表
	
	private List<DNARoomItemVo> roomItemList;//空间家具列表
}
