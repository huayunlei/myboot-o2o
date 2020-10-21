package com.ihomefnt.o2o.intf.domain.hbms.dto;

import com.ihomefnt.o2o.intf.domain.hbms.vo.response.NodeReponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 
 * @author wangjin
 *
 */
@Data
@ApiModel(description = "项目信息")
public class GetUnhandleProjectResultDto {

	@ApiModelProperty(value = "楼盘")
	private String building;

	@ApiModelProperty(value = "节点信息")
	private List<NodeReponse> nodeVos;

	@ApiModelProperty(value = "项目id")
	private String projectId;

	@ApiModelProperty(value = "中期款状态 0-未收 1-已收")
	private String mediumMoneyStatus;

	@ApiModelProperty(value = "尾款状态 0-未收 1-已收")
	private String lastMoneyStatus;
}
