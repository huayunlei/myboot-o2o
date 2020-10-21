package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 空间集合返回值
 * @author ZHAO
 */
@Data
public class SpaceResponse {
	private Integer spaceId;//空间ID
	
	private String spaceName;//空间名称
	
	private List<Integer> bindGroup;//空间分组
	
	private List<SpaceProgramListResponseVo> spaceProgramList;//空间可选方案集合

	public SpaceResponse() {
		this.spaceId = -1;
		this.spaceName = "";
		this.bindGroup = new ArrayList<>();
		this.spaceProgramList = new ArrayList<>();
	}
}
