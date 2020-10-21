package com.ihomefnt.o2o.intf.domain.personalneed.dto;

import lombok.Data;

import java.util.List;

/**
 * @author huayunlei
 *	DNA空间信息
 */
@Data
public class DesignDnaRoomVo {
	
	private Integer dnaId;//	BetaDnaId,
	private String dnaName;//	DNA名称,
	private Integer drDnaId;//	DrDnaId,
	private Integer dnaRoomId;//	贝塔DNA空间id,
	private Integer drDnaRoomId;//	DR空间id,
	private Integer dnaRoomUsageId;//	空间用途id,
	private String dnaRoomUsageDesc;//	空间用途描述=空间名称,
	private String dnaRoomPicUrl;//	空间图片首图,
	private String dnaRoomDesignIdea;//	空间设计理念,
	private Integer dnaStyle;//	 风格id,
	private String dnaStyleStr;//	风格名称,
	private Integer userSelected;//	是否用户自选0：否 1：是
	
	private List<String> roomItemBrandList;// 空间品牌文案列表
	
	private String dnaRoomBrandPraise;// 空间品牌文案
	
}
