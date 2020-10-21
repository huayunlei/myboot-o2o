package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

/**
 * 视频对象实体
 * @author ZHAO
 */
@Data
public class VideoEntity {
	private String videoId = "";//视频ID
	
	private String headImgUrl = "";//首图

	private String headImgSmallUrl = "";//首图小图
	
	private String type = "";//类别
	
	private String name = "";//名称
	
	private String videoUrl = "";//视频路径
	
	private String praise = "";//视频文案
}
