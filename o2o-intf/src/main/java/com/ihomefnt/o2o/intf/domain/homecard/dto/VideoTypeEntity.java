package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

/**
 * 视频类型
 * @author ZHAO
 */
@Data
public class VideoTypeEntity {
	private Integer id;//类型ID
	
	private String code;//分类名称
	
	private String desc;//分类说明
}
