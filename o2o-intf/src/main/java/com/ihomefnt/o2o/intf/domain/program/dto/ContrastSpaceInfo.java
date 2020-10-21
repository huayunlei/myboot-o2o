package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * 对比空间信息
 * Author: ZHAO
 * Date: 2018年6月22日
 */
@Data
public class ContrastSpaceInfo {

	private Integer roomId;//空间id
	
	private String roomUsageName;//空间功能名称

	private Integer roomUsageId;//空间功能名称
	
	private String roomHeadImgURL;//空间首图地址
	
	private List<String> roomImgList;//空间图片集合
	
	private List<FurnitureClassifyInfo> furnitureClassifyList;//家具分类信息

}
