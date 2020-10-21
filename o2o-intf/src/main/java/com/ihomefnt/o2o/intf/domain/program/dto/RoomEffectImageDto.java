package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderRoomExtDataDto;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by acer on 2018/4/24.
 */
@Data
@Accessors(chain = true)
public class RoomEffectImageDto {

	@ApiModelProperty(value = "空间id")
	private Integer roomId;

	@ApiModelProperty(value = "空间效果图片")
	private List<String> pictureUrls;

	@ApiModelProperty("离线渲染图片")
	private OffLineMessageDto newPictureDto;

	@ApiModelProperty("空间图片任务类型,0：不存在任务，1：正常渲染任务，2：失败渲染任务")
	private Integer taskType = 0;

	@ApiModelProperty("仅供参考提示 0 不提示 1提示 默认0")
	private Integer referenceOnlyFlag = Constants.REFERENCE_ONLY_NO_SHOW;

}
