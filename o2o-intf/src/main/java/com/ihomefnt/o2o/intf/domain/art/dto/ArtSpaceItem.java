package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 艺术家
* @Title: ArtSpace.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月15日 下午1:23:10 
* @version V1.0
 */
@Data
public class ArtSpaceItem implements Serializable{

	private int spaceType; //空间类型

	private String spaceName; //空间名称
}
