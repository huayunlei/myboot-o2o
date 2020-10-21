package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.program.dto.FurnitureEntity;
import com.ihomefnt.o2o.intf.domain.program.dto.ImageAspectRatioEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 空间详情返回值
 * @author ZHAO
 */
@Data
public class SpaceDetailResponse {
	private Integer programId;//方案ID
	
	private String programName;//方案名称
	
	private String seriesName;//套系名称
	
	private String styleName;//风格名称
	
	private Integer spaceId;//空间ID
	
	private String spaceName;//空间名称

	private Integer spaceUseId;//空间用途ID
	
	private String spaceUseName;//空间用途名称
	
	private String spacePrice;//空间价格
	
	private String spaceDesc;//空间说明
	
	private List<String> spaceImgList;//空间图片集合
	
	private List<ImageAspectRatioEntity> spaceImageList;//空间图片集合
	
	private List<FurnitureEntity> furnitureList;//家具清单集合
	
	private Integer furnitureNum;//家具数量
	
	private String spaceCategory;//类别：硬装+软装
	
	private String discount;//折扣
	
	private List<String> furnitureGiftList;//家具赠品清单

	private Integer spaceDescType;//空间描述类型：1设计描述、2硬装描述
	
	/**
	 * 设计师列表
	 */
	private List<DesignerInfoForProgramResponse> designerList;

	private Integer commutativeNum;//可替换的源数目

	private List<FurnitureEntity> giftList;//家具赠品清单

	public SpaceDetailResponse() {
		this.programId = 0;
		this.programName = "";
		this.seriesName = "";
		this.styleName = "";
		this.spaceId = 0;
		this.spaceName = "";
		this.spaceUseId = 0;
		this.spaceUseName = "";
		this.spacePrice = "";
		this.spaceDesc = "";
		this.spaceImgList = new ArrayList<>();
		this.furnitureList = new ArrayList<>();
		this.furnitureNum = 0;
		this.spaceCategory = "";
		this.discount = "";
		this.furnitureGiftList = new ArrayList<>();
		this.spaceDescType = 1;
		this.commutativeNum = 0;
		this.giftList = new ArrayList<>();
	}

}
