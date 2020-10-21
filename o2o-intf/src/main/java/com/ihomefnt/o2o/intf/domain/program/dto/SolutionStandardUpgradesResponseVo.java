/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月3日
 * Description:SolutionStandardUpgradesResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class SolutionStandardUpgradesResponseVo implements Serializable {

	private List<StandardUpgradeResponseVo> floorBoardUpgrades;// 地板升级

	private List<StandardUpgradeResponseVo> floorTileUpgrades;// 地砖升级

	private List<StandardUpgradeResponseVo> bathRoomUpgrades;// 卫浴升级

	private List<StandardUpgradeResponseVo> kitchenUpgrades;// 厨房升级

	private List<StandardUpgradeResponseVo> wallpaperUpgrades;// 墙面升级

}
