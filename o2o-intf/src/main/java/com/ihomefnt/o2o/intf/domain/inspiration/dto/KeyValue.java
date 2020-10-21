/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import lombok.Data;

/**
 * @author zhang
 *
 */
@Data
public class KeyValue {

	private int key;// 文章类型的键

	private String value;// 文章类型的值
	
	private int serialNum;//所在索引位置
}
