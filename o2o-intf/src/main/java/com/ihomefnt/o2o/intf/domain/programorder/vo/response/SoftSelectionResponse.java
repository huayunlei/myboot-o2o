package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.SpaceDesign;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ZHAO
 */
@Data
@ApiModel("查询软装选配项")
public class SoftSelectionResponse {

	@ApiModelProperty("空间设计列表")
	private List<SpaceDesign> spaceDesignList;//软装替换空间集合
}
