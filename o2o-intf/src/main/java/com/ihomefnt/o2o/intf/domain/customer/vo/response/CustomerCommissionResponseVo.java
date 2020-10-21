package com.ihomefnt.o2o.intf.domain.customer.vo.response;

import lombok.Data;

@Data
public class CustomerCommissionResponseVo {
	private Integer total;//佣金总额
	private Integer store;//到店佣金
	private Integer order;//交易佣金
}
