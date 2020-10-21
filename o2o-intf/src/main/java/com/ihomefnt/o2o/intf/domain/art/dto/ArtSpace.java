package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.util.List;

/**
 * 艺术家列表页标签
* @Title: ArtSpace.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月16日 下午10:29:06 
* @version V1.0
 */
@Data
public class ArtSpace {

	private int type;
	
	private String name;
	
	private List<ArtSpaceItem> spaceItems;
}
