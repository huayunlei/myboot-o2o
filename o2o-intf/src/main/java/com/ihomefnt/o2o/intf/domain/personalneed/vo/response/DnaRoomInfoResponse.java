package com.ihomefnt.o2o.intf.domain.personalneed.vo.response;

import com.ihomefnt.o2o.intf.domain.personalneed.dto.DnaRoomPicDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DnaRoomInfoResponse {

// todo 去除空间标识
//	@ApiModelProperty("用户选择的空间用途")
//	private Integer roomUsageId;
//
//	@ApiModelProperty("用户选择的用途描述")
//	private String roomUsageName;

	private String dnaRoomPicUrl;// 空间图片首图,
	private String dnaRoomDesignIdea;// 空间设计理念,
	private Integer dnaStyle;// 风格id,
	private String dnaStyleStr;// 风格名称,
	private Integer dnaId;// dnaId,
	private String dnaName ;// DNA名称,
	private Integer dnaRoomId;// dnaRoomId,
	private Integer dnaRoomUsageId;//空间用途id,
	private String dnaRoomUsageDesc;// 空间用途描述=空间名称
	
	private List<String> roomItemBrandList;// 空间品牌文案列表
	
	private String dnaRoomBrandPraise;// 空间品牌文案

	private List<DnaRoomPicDto> dnaRoomPicUrlDtoList;// 空间图片列表

	private List<String> dnaRoomPicUrlList;// 空间图片列表

	private String skipUrl;//跳转链接地址
}
