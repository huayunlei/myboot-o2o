package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 艺术家组织
* @Title: ArtStudio.java 
* @Description: TODO
* @author Charl 
* @date 2016年9月7日 下午5:09:39 
* @version V1.0
 */
@Data
public class ArtStudio implements Serializable{

	private static final long serialVersionUID = -886534504115896060L;
	private Integer studioId;//组织id
	
	private String image;//组织头像
	
	private String name;//组织名称
	
	private String desc;//组织描述
}
