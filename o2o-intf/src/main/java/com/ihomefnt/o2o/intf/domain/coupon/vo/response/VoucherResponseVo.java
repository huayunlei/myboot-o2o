package com.ihomefnt.o2o.intf.domain.coupon.vo.response;

import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import lombok.Data;

import java.util.List;



/**
 * @author zhang
 * 抵用券响应类
 */
@Data
public class VoucherResponseVo {
	
	private List<Voucher> voucherList;//抵用券集合
	
	private Voucher voucher;//对应抵用券，给H5用的
}
