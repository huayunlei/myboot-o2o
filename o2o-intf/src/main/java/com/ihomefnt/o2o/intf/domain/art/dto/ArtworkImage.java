package com.ihomefnt.o2o.intf.domain.art.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 艺术品图片
* @Title: ArtworkImage.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月18日 下午2:39:40 
* @version V1.0
 */
@Data
@ApiModel("ArtworkImage")
public class ArtworkImage implements Serializable{

	private static final long serialVersionUID = 9068898609105732665L;
	@ApiModelProperty("图片id")
	private long imageId; //艺术品图片id

	@ApiModelProperty("图片url")
	private String imageUrl; //艺术品图片url

	@ApiModelProperty("图片描述")
	private String description;  //图片描述

	@ApiModelProperty("图片排序")
	private int order;  //图片排序

	@ApiModelProperty("图片宽度")
	private int width;  //图片宽度

	@ApiModelProperty("图片高度")
	private int height;  //图片高度
}
