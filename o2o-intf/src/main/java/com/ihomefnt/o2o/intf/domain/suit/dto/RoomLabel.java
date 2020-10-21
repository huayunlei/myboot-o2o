/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

import java.util.List;

/**
 * @author wang<br/>
 *         空间标签对象<br/>
 *
 */
@Data
public class RoomLabel {

	private String brandJson;//品牌选项

	private String styleJson;// 空间风格集合

	private String storageJson;// 储物空间集合

	private String materialJson;// 材质集合

	private String temperamentJson;// 气质集合

	private String colorJson;// 颜色集合
	
	private List<String> brandList;//品牌选项（转list返回）

	private List<String> styleList;// 空间风格集合（转list返回）

	private List<String> storageList;// 储物空间集合（转list返回）

	private List<String> materialList;// 材质集合（转list返回）

	private List<String> temperamentList;// 气质集合（转list返回）

	private List<String> colorList;// 颜色集合（转list返回）

}
