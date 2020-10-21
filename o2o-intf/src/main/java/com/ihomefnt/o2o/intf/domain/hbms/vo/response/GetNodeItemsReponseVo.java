package com.ihomefnt.o2o.intf.domain.hbms.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author wangjin
 *
 */
@Data
@ApiModel(value = "验收项详情")
public class GetNodeItemsReponseVo {

	@ApiModelProperty(value = "时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date checkTime;

	@ApiModelProperty(value = "资料")
	private List<DatumReponseVo> datums;

	@ApiModelProperty(value = "隐患")
	private List<ItemReponseVo> items;

	@ApiModelProperty(value = "节点id")
	private String nodeId;

	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	@ApiModelProperty(value = "节点状态")
	private String nodeStatus;
	
}
