package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * APP3.0新版首页产品版块返回值
 * @author ZHAO
 */
@Data
@ApiModel(value="ProductBoardResponse",description="APP3.0新版首页产品版块返回值")
public class ProductBoardResponse {
	@ApiModelProperty("产品ID")
	private Integer productId;

	@ApiModelProperty("首图切图")
	private String headImgUrl;

	@ApiModelProperty("风格")
	private String style;
	
	@ApiModelProperty("名称")
	private String name;
	
	@ApiModelProperty("文案")
	private String praise;
	
	@ApiModelProperty("喜欢的人数")
	private Integer favoriteNum;

	@ApiModelProperty("评论数")
	private Integer commentNum;

	@ApiModelProperty("转发数")
	private Integer forwardNum;

	@ApiModelProperty("浏览数")
	private Integer visitNum;

	@ApiModelProperty("是否支持3D巡游： true有，false无")
	private boolean support3D = false;

	@ApiModelProperty("是否有视频： true有，false无")
	private boolean hasVideo = false;

	public ProductBoardResponse() {
		this.productId = 0;
		this.headImgUrl = "";
		this.style = "";
		this.name = "";
		this.praise = "";
		this.favoriteNum = 0;
		this.commentNum = 0;
		this.forwardNum = 0;
		this.visitNum = 0;
	}
}
