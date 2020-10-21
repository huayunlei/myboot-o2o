package com.ihomefnt.o2o.intf.domain.homecard.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * DNA详情意境倡导版块
 * @author ZHAO
 */
@Data
public class ArtisticListEntity {
	private String artisticTitle = "";//意境版块标题：意境
	
	private List<ArtisticEntity> artisticList = Lists.newArrayList();//意境集合
}
