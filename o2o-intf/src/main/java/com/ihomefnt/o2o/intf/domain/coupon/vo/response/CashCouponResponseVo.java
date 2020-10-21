package com.ihomefnt.o2o.intf.domain.coupon.vo.response;

import com.ihomefnt.o2o.intf.domain.coupon.doo.CashAccountDo;
import com.ihomefnt.o2o.intf.domain.coupon.doo.CashCouponDo;
import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import lombok.Data;

import java.util.List;

@Data
public class CashCouponResponseVo {
	
	private Double voucherTotal; //所有抵用券总额
	
	private List<Voucher> voucherEnableList;//能用抵用券集合

	private List<CashCouponDo> cashCouponList;
	
	private List<CashAccountDo> cashAccount;
}
