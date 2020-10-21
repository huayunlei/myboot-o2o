package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArtStockCountInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6722490595104268979L;
	// 库存总数
	private int allInventory;
	// 待售库存总数
	private int enableInventory;
	// 已售库存总数
	private int allSaledCount;
	//冻结库存数
	private int freezeInventory;
	//已售库存数
	private int deducteInventory;
	// 艺术品id
	private int productId;
}

