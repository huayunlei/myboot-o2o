package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by acer on 2018/9/6.
 */
@Data
public class ClassifyDetailInfo extends RightsClassifyDto {

	@ApiModelProperty("当前list")
	private List<RigthtsItemBaseInfo> currentList;

	@ApiModelProperty("可升级项")
	private List<RigthtsItemBaseInfo> futureList;
}
