package com.ihomefnt.o2o.intf.domain.program.vo.response;


import lombok.Data;

/**
 * 软装替换空间信息
 * @author ZHAO
 */
@Data
public class OptionalSoftSpaceResponse extends OptionalSoftResponse{

	private Integer spaceId;//空间id
	
	private String spaceName;//空间名称
	
	private Integer spaceUseId;//空间用途ID
	
	private String spaceUseName;//空间用途名称

	private String visualImg;//可视化空间选配默认图

	public OptionalSoftSpaceResponse() {
		this.spaceId = 0;
		this.spaceName = "";
		this.spaceUseId = 0;
		this.spaceUseName = "";
	}

}
