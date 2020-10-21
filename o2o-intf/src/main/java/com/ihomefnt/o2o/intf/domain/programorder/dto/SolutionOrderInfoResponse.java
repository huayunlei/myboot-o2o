package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.SolutionRoomDetailVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author chong
 */
@Data
@ApiModel("订单方案详情响应信息")
public class SolutionOrderInfoResponse {

	@ApiModelProperty("已选方案信息")
	private AladdinProgramInfoVo solutionSelectedInfo;
	
	@ApiModelProperty("方案空间列表")
	private List<SolutionRoomDetailVo> solutionRoomDetailVoList;
}
