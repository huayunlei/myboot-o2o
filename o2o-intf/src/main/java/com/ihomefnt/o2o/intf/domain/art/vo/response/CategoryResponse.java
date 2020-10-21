package com.ihomefnt.o2o.intf.domain.art.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 艺术品分类
 * @author ZHAO
 */
@Data
@ApiModel("艺术品首页分类返回")
public class CategoryResponse {
	@ApiModelProperty("分类类别")
	private Integer categoryType;

	@ApiModelProperty("分类标题")
	private String categoryTitle;

	@ApiModelProperty("分类副标题")
	private String categorySubTitle;

	@ApiModelProperty("分类图片")
	private String categoryImgUrl;

	@ApiModelProperty("跳转到列表页的标题")
	private String jumpTitle;
	
}
