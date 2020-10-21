package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

/**
 * 空间方案返回值
 * @author ZHAO
 */
@Data
public class SpaceProgramResponse {
	private Integer spaceSeriesId;//套系ID
	
	private String spaceSeriesName;//套系名称
	
	private Integer programId;//方案ID
	
	private String name;//方案名称
	
	private String programDiscount;//方案折扣
	
	private String style;//风格
	
	private Integer roomId;//方案空间ID
	
	private String spaceName;//方案空间名称
	
	private Integer furnitureNum;//空间家具数量
	
	private String spacePrice;//空间价格
	
	private String headImgUrl;//空间头图
	
	private String category;//装修类别：硬装+软装

	public SpaceProgramResponse() {
		this.spaceSeriesId = 0;
		this.spaceSeriesName = "";
		this.programId = 0;
		this.name = "";
		this.programDiscount = "";
		this.style = "";
		this.roomId = 0;
		this.spaceName = "";
		this.furnitureNum = 0;
		this.spacePrice = "";
		this.headImgUrl = "";
		this.category = "";
	}
}
