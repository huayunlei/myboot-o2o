package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("已选DNA空间")
@Data
public class CommitDesignDnaRoom {
	
	@ApiModelProperty("DNA空间id")
	private Integer dnaRoomId;
	
	@ApiModelProperty("DNA ID")
	private Integer dnaId;
	
	@ApiModelProperty("用户选择的空间用途")
	private Integer roomUsageId;

	@ApiModelProperty("用户选择的用途描述")
	private String roomUsageName;

	@ApiModelProperty("是否用户自选0:否 1：是")
	private Integer userSelected;
	
	/**
     *户型空间id
     */
    @ApiModelProperty("户型空间id")
    private Long roomId;


	private String dnaName;//	DNA名称,
	private Integer dnaRoomUsageId;//	默认空间用途id,
	private String dnaRoomUsageDesc;//	默认空间用途描述=空间名称,
	private String dnaRoomPicUrl;//	空间图片首图,
	private String dnaRoomDesignIdea;//	空间设计理念,
	private Integer dnaStyle;//	 风格id,
	private String dnaStyleStr;//	风格名称,
	private List<String> roomItemBrandList;// 空间品牌文案列表

	private String dnaRoomBrandPraise;// 空间品牌文案
}
