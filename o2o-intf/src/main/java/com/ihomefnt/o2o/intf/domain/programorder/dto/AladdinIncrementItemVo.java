package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 增减项信息
 * @author ZHAO
 */
@Data
public class AladdinIncrementItemVo {
	private BigDecimal dealAmount;//软、硬增减项总成交金额
	
	private List<AppValetOrderInfoSoftDetailVo> softFitmentInfo;//软装增减项信息
	
	private List<AladdinStrongFitmentDetailVo> strongIncreaseInfo;//硬装增项信息
	
	private List<AladdinStrongFitmentDetailVo> strongDecreaseInfo;//硬装减项信息
}
