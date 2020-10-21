/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.house.dto;

import lombok.Data;

/**
 * @author weitichao
 *
 */
@Data
public class ModelSuitDto {
	
	private int suitId;  //套装id

	private String suitName;  //套装名字
	
	private String suitStyleName;  //套装风格名字
	
	private String suitImage;  //套装头图
	
	private String suit3VHomeImage;  //套装的3V家链接

}
