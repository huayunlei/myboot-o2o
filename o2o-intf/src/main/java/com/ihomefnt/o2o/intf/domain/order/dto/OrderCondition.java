/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProduct;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 *
 */
@Data
public class OrderCondition {
	
	Long userId;
	
	String mobile;
	
	String user;	
	
    Double orderPrice;
    
    CompositeProduct compositeProduct; 
    
    List<ProductOrder> productOrderList; 
    
    Map<Long, Double> id2Price;Integer osType;
    
    boolean whole;
    
    int isUseCoupon;
    
    private Long voucherId;// 抵用券领取ID,存放的是t_voucher_detail表主键 
}
