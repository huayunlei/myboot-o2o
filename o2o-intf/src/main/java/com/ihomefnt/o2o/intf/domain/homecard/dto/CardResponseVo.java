package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

/**
 * 卡片信息
 * @author ZHAO
 */
@Data
public class CardResponseVo {
	private Integer fk;//外键
	
	private Integer id;//卡片ID
	
	private Integer type;//卡片类别ID
	
	private String typeDesc;//卡片类别名称
	
	private String name;//名称
	
	private String title;//标题
	
	private String subTitle;//副标题
	
	private String headImgUrl;//头图
	
	private String style;//风格
	
	private String praise;//文案
	
	private String idea;//设计理念
	
	private String url;//跳转路径
	
	private String appVersion;//版本号

}
