package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 升级项
 * @author ZHAO
 */
@Data
public class UpgradeInfo {
	private List<ItemInfo> standardUpgradeList;//标准升级项
	
	private List<ItemInfo> nonstandardUpgradeList;//非标准升级项

	public UpgradeInfo() {
		this.standardUpgradeList = new ArrayList<>();
		this.nonstandardUpgradeList = new ArrayList<>();
	}
}
