package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import lombok.Data;

/**
 * 视频类型返回值
 * @author ZHAO
 */
@Data
public class VideoTypeResponse {
	private Integer typeId;//视频类型
	
	private String typeName;//类型名称

	public VideoTypeResponse() {
		this.typeId = 0;
		this.typeName = "";
	}

}
