package com.ihomefnt.o2o.intf.domain.homecard.dto;


import lombok.Data;

/**
 * DNA详情页空间图片实体
 * @author ZHAO
 */
@Data
public class DnaRoomImageEntity {
	private String roomName = "";//空间名称
	
	private String imgUrl = "";//空间图片

	private String bigImgUrl = "";// 空间图片-大一点的

	private Integer imgWidth = 0;//图片宽度

	private Integer imgHeight = 0;//图片高度
	
	private String roomPraise = "";//空间文案
	
	private String brandPraise = "";//品牌文案
	
	private Integer usedCount = 0;//方案使用次数字段

}
