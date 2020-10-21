package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.RoomBaseDetailDto;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ServiceItemDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 需求确认清单
 * @author ZHAO
 */
@Data
public class AladdinOrderDetailResponseVo {
	private Integer orderNum;//订单编号
	
	@ApiModelProperty("区分新老订单类型:0老订单 1:新订单")
    private Integer newOrderState;
	
	private String adviseMoblie;//置家顾问电话

	@ApiModelProperty("订单类型 0：方案 6：代客下单")
	private Integer orderSource;

	@ApiModelProperty("beta端变动标识 0：未变动 1：发生变动")
	private Integer betaChangeFlag = 0;


	private AladdinRequireVo requiredDto;//需求确认信息
	
	private List<AladdinSoftSpaceVo> softBaseList;//软装基础商品

	private List<AladdinSoftItemVo> softAddBagList;//软装增配包

	private List<AladdinSoftItemVo> softFitemList;//增减项

	private List<AladdinSoftItemVo> softPresentList;//软装赠品
	
	private List<AladdinHardItemVo> hardInFitmentList;//硬装增项

	private List<AladdinHardItemVo> hardDeFitmentList;//硬装减项

	private List<AladdinHardItemVo> upgradeInfoList;//标准升级项

	private List<AladdinHardItemVo> noUpgradeInfoList;//非标准升级项

	private List<AladdinHardItemVo> hardAddBagList;//硬装增配包

	@ApiModelProperty("订单总额(合同额)")
	private BigDecimal contractAmount;

	@ApiModelProperty("原合同额")
	private BigDecimal originalOrderAmount;

	@ApiModelProperty("5.0新增基础商品list")
	private List<RoomBaseDetailDto> baseList;

	@ApiModelProperty("服务费明细")
	private List<ServiceItemDto> serviceItemList;

}
