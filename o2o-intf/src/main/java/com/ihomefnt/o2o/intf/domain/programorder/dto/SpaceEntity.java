package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.FurnitureEntity;
import com.ihomefnt.o2o.intf.domain.program.dto.ImageAspectRatioEntity;
import com.ihomefnt.o2o.intf.domain.program.dto.ReplaceImageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 空间详情
 * @author ZHAO
 */
@Data
public class SpaceEntity implements Serializable {
	
	private Integer roomId;//空间id
	
	private String spaceName;//空间名称
	
	private List<String> spaceImgList;//空间图片集合
	
	private List<ImageAspectRatioEntity> spaceImageList;//空间图片集合
	
	private List<FurnitureEntity> furnitureList;//家具清单

	private Integer furnitureNum;//家具总件数
	
	private String spacePrice;//空间价格
	
	private String spaceDesc;//空间描述
	
	private String spaceCategory;//装修类别：硬装+软装
	
	private List<String> furnitureGiftList;//家具赠品清单
	
	private String spaceSeriesName;//空间方案套系
	
	private String spaceStyleName;//空间方案风格

	private Integer spaceDescType;//空间描述类型：1设计描述、2硬装描述
	
	private Integer commutativeNum;//可替换的源数目

	private List<FurnitureEntity> giftList;//家具赠品清单
	
	private List<String> replaceSpaceImgList;//可替换空间图片

	private List<ReplaceImageInfo> replaceImgList;//可替换空间图片

	public SpaceEntity() {
		this.roomId = 0;
		this.spaceName = "";
		this.spaceImgList = new ArrayList<>();
		this.furnitureList = new ArrayList<>();
		this.furnitureNum = 0;
		this.spacePrice = "";
		this.spaceDesc = "";
		this.spaceCategory = "";
		this.furnitureGiftList = new ArrayList<>();
		this.spaceSeriesName = "";
		this.spaceStyleName = "";
		this.spaceDescType = 1;
		this.commutativeNum = 0;
		this.giftList = new ArrayList<>();
		this.replaceSpaceImgList = new ArrayList<>();
		this.replaceImgList = new ArrayList<>();
	}
}
