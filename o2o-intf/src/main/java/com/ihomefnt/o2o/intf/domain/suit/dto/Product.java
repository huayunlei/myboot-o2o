/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author zhang<br/>
 *         商品对象<br/>
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
public class Product {
	
	private Long productId;//商品主键

	private String productName;// 商品名称

	private Long productCount;// 商品数量

	private String productImage;// 商品图片URL
	
	private String brand;// 商品品牌

	private String style;// 商品风格

	private String material;// 商品材质
	
	private String productStr;// 品牌风格材质的拼接字段


	public String getProductStr() {
		StringBuffer buff = new StringBuffer();
		if(brand!=null){
			buff.append(brand);
			buff.append(" | ");
		}
		if(style!=null){
			buff.append(style);
			buff.append(" | ");
		}
		if(material!=null){
			buff.append(material);
		}
		String str =buff.toString();
		if(str.endsWith(" | ")){
			str=str.substring(0, str.length()-" | ".length());
		}
		return str;
	}
}
