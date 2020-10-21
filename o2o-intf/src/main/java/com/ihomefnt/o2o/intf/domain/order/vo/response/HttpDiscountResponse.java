/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.order.vo.response;

import lombok.Data;

/**
 * @author zhang
 *
 */
@Data
public class HttpDiscountResponse {
	
	private Long suitId;//套装ID

	private Double suitDiscount;// 套装折扣

	private Double roomDiscount;// 空间折扣
}
