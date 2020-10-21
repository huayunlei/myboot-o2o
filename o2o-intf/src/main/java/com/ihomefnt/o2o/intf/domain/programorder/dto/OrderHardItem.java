package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.HardStandardSpaceResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 硬装清单
 * @author ZHAO
 */
@Data
public class OrderHardItem {
	private List<HardStandardSpaceResponse> hardSpaceList;//硬装清单

	private UpgradeInfo upgradeInfo;//硬装升级项  类别、商品名称

	private List<ItemInfo> hardAddBagList;//硬装增配包  图片、名称

	private List<ItemInfo> hardIncrementList;//硬装增减项  不展示减项   类目说明、数量、计量单位

	public OrderHardItem() {
		this.hardSpaceList = new ArrayList<>();
		this.upgradeInfo = new UpgradeInfo();
		this.hardAddBagList = new ArrayList<>();
		this.hardIncrementList = new ArrayList<>();
	}

}
