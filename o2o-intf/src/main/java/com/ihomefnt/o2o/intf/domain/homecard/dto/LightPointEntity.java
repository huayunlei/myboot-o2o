package com.ihomefnt.o2o.intf.domain.homecard.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * DNA详情 亮点
 * @author ZHAO
 */
@Data
public class LightPointEntity {
	private Integer type = -1;//类别：1硬装 2软装
	
	private String lightPointTitle = "";//标题：软装清单、硬装清单
	
	private String lightPointSubTitle = "";//副标题
	
	private List<String> point = Lists.newArrayList();//亮点
	
	private String skipUrl = "";//清单跳转路径
}
