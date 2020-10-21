package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainUserInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.ArtImageEntity;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.ProductProgramEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * APP3.0新版首页推荐版块返回值
 * @author ZHAO
 */
@Data
@ApiModel(value="RecommendBoardResponse",description="APP3.0新版首页推荐版块返回值")
public class RecommendBoardResponse {
	@ApiModelProperty("卡片ID")
	private Integer cardId;
	
	@ApiModelProperty("卡片类型（1DNA、2样板套装、3banner、4艺术品、5视频、6特定产品方案）")
	private Integer type;
	
	@ApiModelProperty("首图切图")
	private String headImgUrl;
	
	@ApiModelProperty("艺术品图片")
	private List<ArtImageEntity> imgObj;
	
	@ApiModelProperty("风格")
	private String style;
	
	@ApiModelProperty("主标题")
	private String title;
	
	@ApiModelProperty("名称/副标题")
	private String name;
	
	@ApiModelProperty("文案")
	private String praise;
	
	@ApiModelProperty("理念")
	private String idea;
	
	@ApiModelProperty("喜欢的人数")
	private Integer favoriteNum;
	
	@ApiModelProperty("评论的个数")
	private Integer commentNum;
	
	@ApiModelProperty("视频路径")
	private String videoUrl;
	
	@ApiModelProperty("跳转路径")
	private String skipUrl;
	
	@ApiModelProperty("特定产品方案")
	private List<ProductProgramEntity> programList;

	@ApiModelProperty("楼盘名称")
	private String building;

	@ApiModelProperty("楼盘ID")
	private Integer buildingId;

	@ApiModelProperty("房产ID")
	private Integer houseId;

	@ApiModelProperty("户型ID")
	private Integer houseTypeId;
	
	@ApiModelProperty("户型名称")
	private String house;
	
	@ApiModelProperty("硬装标准数")
	private Integer hardNum;

	@ApiModelProperty("软装标准数")
	private Integer softNum;

	@ApiModelProperty("风格数")
	private Integer styleNum;

	@ApiModelProperty("最低价格")
	private Number priceStart;

	@ApiModelProperty("最高价格")
	private Number priceEnd;

	@ApiModelProperty("户型面积")
	private String houseArea;

	@ApiModelProperty("按钮名称")
	private String buttonText;
	
	@ApiModelProperty("报修的用户信息")
	private MaintainUserInfoResponseVo maintainUserInfo;

	@ApiModelProperty("房产信息")
	private String buildingInfo;//房产信息

	public RecommendBoardResponse() {
		this.cardId = 0;
		this.type = 1;
		this.headImgUrl = "";
		this.imgObj = new ArrayList<>();
		this.style = "";
		this.title = "";
		this.name = "";
		this.praise = "";
		this.idea = "";
		this.favoriteNum = -1;
		this.commentNum = -1;
		this.videoUrl = "";
		this.skipUrl = "";
		this.programList = new ArrayList<>();
		this.building = "";
		this.buildingId = 0;
		this.houseId = 0;
		this.houseTypeId = 0;
		this.house = "";
		this.hardNum = 0;
		this.softNum = 0;
		this.styleNum = 0;
		this.priceStart = 0;
		this.priceEnd = 0;
		this.buttonText = "";
		this.buildingInfo="";
		this.maintainUserInfo  = new MaintainUserInfoResponseVo();
	}

}
