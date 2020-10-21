package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DesignFeeResponseVo {

	private Integer userId;// 设计师用户id
	private BigDecimal sumAmount;// 总收益,
	private BigDecimal balance;// 可提现金额,
	private List<DNAFeeResponseVo> dnaFeeList;// DNA收益列表,
	private List<DNAFeeDetailResponseVo> dnaFeeDetailList;// DNA收益明细列表,
	private List<DNAFeePayResponseVo> dnaFeePayList;// 到账明细列表
}
