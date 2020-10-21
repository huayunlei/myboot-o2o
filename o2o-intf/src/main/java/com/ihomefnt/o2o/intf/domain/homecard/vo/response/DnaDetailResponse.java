package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.ArtisticListEntity;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DnaCommentList;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DnaRoomImageEntity;
import com.ihomefnt.o2o.intf.domain.homecard.dto.LightPointList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * APP3.0新版DNA详情返回值
 * @author ZHAO
 */
@Data
@ApiModel(value="DnaDetailResponse",description="APP3.0新版DNA详情返回值")
public class DnaDetailResponse {
	@ApiModelProperty("DNA ID")
	private Integer dnaId;
	
	@ApiModelProperty("首图")
	private String headImgUrl;

	@ApiModelProperty("首图宽度")
	private Integer headImgWidth;

	@ApiModelProperty("首图高度")
	private Integer headImgHeight;
	
	@ApiModelProperty("各空间图片")
	private List<DnaRoomImageEntity> imgList;

	@ApiModelProperty("风格")
	private String style;

	@ApiModelProperty("DNA名称")
	private String name;

	@ApiModelProperty("文案")
	private String praise;

	@ApiModelProperty("价格套系")
	private String priceType;

	@ApiModelProperty("标签")
	private List<String> tagList;

	@ApiModelProperty("设计理念")
	private String idea;

	@ApiModelProperty("意境倡导")
	private ArtisticListEntity artistic;

	@ApiModelProperty("用户评价")
	private DnaCommentList comment;
	
	@ApiModelProperty("喜欢的人数")
	private Integer favoriteNum;
	
	@ApiModelProperty("软硬装亮点")
	private LightPointList lightPoint;
	
	@ApiModelProperty("VR链接")
	private String vrLinkUrl;
	
	@ApiModelProperty("评论权限（0不需登录1需要登录评论）")
	private Integer commentLimitFlag;

	@ApiModelProperty("分享链接")
	private String shareUrl;

	private String dnaTotalPrice;//DNA总价（参考）
	
	private String dnaPricePraise;//价格参考文案
	
	private DesignerResponse designer; // 设计师


	public DnaDetailResponse() {
		this.dnaId = 0;
		this.headImgUrl = "";
		this.headImgWidth = 0;
		this.headImgHeight = 0;
		this.imgList = new ArrayList<>();
		this.style = "";
		this.name = "";
		this.praise = "";
		this.priceType = "";
		this.tagList = new ArrayList<>();
		this.idea = "";
		this.artistic = new ArtisticListEntity();
		this.comment = new DnaCommentList();
		this.favoriteNum = -1;
		this.lightPoint = new LightPointList();
		this.vrLinkUrl = "";
		this.commentLimitFlag = 0;
		this.shareUrl = "";
		this.dnaTotalPrice = "";
		this.dnaPricePraise = "";
	}

}
