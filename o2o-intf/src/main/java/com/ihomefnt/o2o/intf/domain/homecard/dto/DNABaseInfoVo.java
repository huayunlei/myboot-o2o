package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

/**
 * DNA基础信息
 * 
 * @author ZHAO
 */
@Data
public class DNABaseInfoVo {
	private Integer id;// DNAid

	private String name;// DNA名称

	private String headImgUrl;// 首图地址

	private String style;// 风格

	private String praise;// 短文案

	private String idea;// 设计理念

	private Integer type;// 实体类型 1DNA 2套装,

	private boolean support3D = false;// 是否支持3D巡游

	private boolean hasVideo = false;// 是否有视频

}
