/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;


import lombok.Data;

/**
 * @author zhang<br/> 
 * 空间图片实体类<br/>
 */
@Data
public class RoomImage implements Cloneable{

	private Long imageId;// 图片ID
	
	private String imageUrlOrignal;// 图片URL

	private String imageUrl;// 缩放图片URL

	private String roomDesc;// 设计说明<description为IOS关键字,故用roomDesc>
	
	private Long detailpage;////是否用于图文详情页 1：用于图文详情页
	
	private Integer width; //长
	
	private Integer height;//宽

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();

	}
}
