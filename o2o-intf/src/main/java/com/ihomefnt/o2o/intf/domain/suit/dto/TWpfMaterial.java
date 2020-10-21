/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

import java.util.List;

/**
 * @author weitichao
 *
 */
@Data
public class TWpfMaterial {
	
	private int materialId; //材料分类id
	
	private String materialName; //材料分类名称：主材，辅材，家具
	
	private int wpfId; //套装id
	
	private List<TWpfMaterialItem> materialItemList; //材料分类列表

}
