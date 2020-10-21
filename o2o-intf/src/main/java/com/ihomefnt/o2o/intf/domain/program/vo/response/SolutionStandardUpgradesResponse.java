/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月6日
 * Description:SolutionStandardUpgradeResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.program.dto.StandardUpgradeResponseVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class SolutionStandardUpgradesResponse implements Serializable{
	
	private int type ;// // 1地板升级,2  地砖升级, 3卫浴升级,4厨房升级 ,5 墙面升级

	private String upgradeTitle;

	private String upgradeDesc;

	private List<StandardUpgradeResponseVo> upgradeItems;
}
