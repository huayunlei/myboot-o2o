package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

/**
 * 专题信息
 * @author ZHAO
 */
@Data
public class SubjectInfoVo {
	private Integer id;//ID
	
	private String title;//专题主标题
	
	private String subTitle;//专题副标题
	
	private String headImgUrl;//卡片头图
	
	private String bannerUrl;//专题banner
	
	private String subjectDesc;//专题描述
	
	private String subjectImgUrl;//专题描述图片

}
