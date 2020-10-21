package com.ihomefnt.o2o.intf.domain.ordersnapshot.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单快照货物信息
 * @author ZHAO
 */
@Data
public class OrderSnapshotCargoResponse {
	private Integer cargoId;  //货物id
	private String cargoNum;  //货物编号
	private String cargoName;  //货物名称
	private Integer cargoCaregoty;  //货物种类
	private Integer cargoSupplier;  //货物供应商
	private String cargoImage;  //货物头图
	private BigDecimal cargoPurchasePrice;  //货物采购价
	private BigDecimal cargoSellPrice;  //货物售卖价
	private BigDecimal cargoReferencePrice;  //货物市场参考价
	private String manufacturerModel;  //货物厂家型号
	private Integer cargoLong;  //货物长度
	private Integer cargoWidth;  //货物宽度
	private Integer cargoHigh;  //货物高度
	private Integer cargoState;  //货物状态（1.上架 0.下架 -1.删除）
}
