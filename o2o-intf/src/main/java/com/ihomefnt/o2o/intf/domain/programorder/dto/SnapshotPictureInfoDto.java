package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 空间图片信息
 * @author liguolin
 *
 */
@Data
@ApiModel("空间图片信息")
public class SnapshotPictureInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3775173383848055919L;
	
	/**
	 * 方案空间效果图URL
	 */
	@ApiModelProperty("方案空间效果图URL")
	private String solutionRoomPicURL;
	
	/**
	 * 是否首图标志位 0不是 1是
	 */
	@ApiModelProperty("是否首图标志位 0不是 1是")
	private Integer isFirst;

	/**
	 * 图片类型 0-正常 1-效果图
	 */
	@ApiModelProperty("图片类型 0-正常 1-效果图")
	private Integer type;
}
