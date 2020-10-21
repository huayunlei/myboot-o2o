/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.coupon.dto;

import lombok.Data;

/**
 * @author zhang
 *
 */
@Data
public class VoucherLog {
		
	private Long voucherId;// 抵用券领取ID,存放的是t_voucher_detail表主键
	
	private Long voucherStatus ;//操作后的抵用券状态1:待确认收款2:已生效3:已使用4:已失效5:使用中
	
	private Long operateType; //操作类型 1:注册 2:确认收款 3:使用 ,4取消

}
