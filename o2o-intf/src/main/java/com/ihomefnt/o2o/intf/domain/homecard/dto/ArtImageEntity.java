package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

/**
 * APP3.0新版首页图片对象
 * @author ZHAO
 */
@Data
public class ArtImageEntity {
	private String artId = "";//艺术品ID
	
	private String artName = "";//艺术品名称
	
	private String artImgUrl = "";//图片Url
	
	private String skipUrl = "";//跳转地址

}
