/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月6日
 * Description:SolutionStandardUpgradesResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class SolutionStandardUpgradeTotalResponse implements Serializable{

	private int standardUpgradeTotal; // 总共升级项

	private List<SolutionStandardUpgradesResponse> upgradesList;
}
