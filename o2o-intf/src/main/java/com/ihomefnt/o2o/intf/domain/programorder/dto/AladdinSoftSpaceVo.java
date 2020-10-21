package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.RoomPictureDto;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 软装空间信息
 * @author ZHAO
 */
@Data
public class AladdinSoftSpaceVo {
	private Integer roomId;//空间ID
	
	private String roomName;//空间名称

	private Integer furnitureType;//商品类型

	private List<AladdinSoftItemVo> softItemList;//软装基础商品

	private List<RoomPictureDto> roomPictureDtoList;

	@ApiModelProperty("空间图片任务类型,0：不存在任务，1：正常渲染任务，2：失败渲染任务")
	private Integer taskType = 0;

	@ApiModelProperty("仅供参考提示 0 不提示 1提示 默认0")
	private Integer referenceOnlyFlag = Constants.REFERENCE_ONLY_NO_SHOW;
}
