package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.util.List;

/**
 * 艺术品列表页标签
* @Title: ArtStyle.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月15日 下午5:20:24 
* @version V1.0
 */
@Data
public class ArtStyle {
	
	private int styleId;  //大分类id
	
	private String name;  //大分类的名称：绘画，雕塑
	
	List<ArtSpaceItem> spaceList; //空间列表
}
