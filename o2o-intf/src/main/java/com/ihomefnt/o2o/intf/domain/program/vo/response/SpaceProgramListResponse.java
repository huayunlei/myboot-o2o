package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 空间选择方案返回值
 * @author ZHAO
 */
@Data
public class SpaceProgramListResponse {
	
	private List<SpaceProgramResponse> spaceProgramList;//空间方案集合
}
