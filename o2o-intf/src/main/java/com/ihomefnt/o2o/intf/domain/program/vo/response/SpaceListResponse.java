package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 各空间方案集合
 * @author ZHAO
 */
@Data
public class SpaceListResponse {
	private String bindDesc;//空间绑定说明
	
	private List<SpaceResponse> spaceList;

	public SpaceListResponse() {
		this.bindDesc = "";
		this.spaceList = new ArrayList<>();
	}

}
