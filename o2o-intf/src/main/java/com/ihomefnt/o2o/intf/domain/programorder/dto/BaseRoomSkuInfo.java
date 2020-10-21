package com.ihomefnt.o2o.intf.domain.programorder.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @author liguolin
 *
 */
@Data
@NoArgsConstructor
@ApiModel("空间基本信息")
public class BaseRoomSkuInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1888669921812265658L;
	
	
	/**
	 * 空间id
	 */
	@ApiModelProperty("空间id")
	private Integer roomId;
	
	/**
	 * 空间名称
	 */
	@ApiModelProperty("空间名称")
	private String roomName;
	
	/**
	 * 设计风格
	 */
	@ApiModelProperty("设计风格")
	private String solutionStyleName;
	
	/**
	 * 空间图片
	 */
	@ApiModelProperty("空间图片")
	private String roomImage;
	
	/**
	 * 套系名称
	 */
	@ApiModelProperty("套系名称")
	private String suitName;
	
	/**
	 * 方案名称
	 */
	@ApiModelProperty("方案名称")
	private String solutionName;
	

	public BaseRoomSkuInfo(Integer roomId, String roomName, String solutionStyleName, String roomImage, String suitName,
			String solutionName) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.solutionStyleName = solutionStyleName;
		this.roomImage = roomImage;
		this.suitName = suitName;
		this.solutionName = solutionName;
	}
}
