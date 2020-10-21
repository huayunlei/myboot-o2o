/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

/**
 * @author weitichao
 *
 */
@Data
public class TWpfMaterialItem {
	
	private int materialItemId; //具体材料id
	
	private String materialItemName; //具体材料名称
	
	private String materialItemUrl; //材料内容url
	
	private int materialDisplayOrder;  //材料显示顺序
	
}
