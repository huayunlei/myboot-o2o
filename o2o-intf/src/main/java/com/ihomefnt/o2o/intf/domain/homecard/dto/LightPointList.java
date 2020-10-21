package com.ihomefnt.o2o.intf.domain.homecard.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * DNA详情软硬装亮点版块
 * @author ZHAO
 */
@Data
public class LightPointList {
	private String title = "";//亮点版块标题：包含
	
	private String subTitle = "";//亮点版块副标题：硬装+软装=全品家
	
	private List<LightPointEntity> pointList = Lists.newArrayList();//亮点
}
