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
@ApiModel(description = "节点")
public class NodeReponse {

	@ApiModelProperty(value = "预计验收时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date expectCheckTime;

	@ApiModelProperty(value = "节点id")
	private String id;

	@ApiModelProperty(value = "验收项数")
	private Integer itemNumber;

	@ApiModelProperty(value = "验收项")
	private List<ItemReponseVo> items;

	@ApiModelProperty(value = "节点")
	private String nodeName;

	@ApiModelProperty(value = "节点状态：0-初始化 1-待验收 2-待整改 3-逾期 4-已整改 5-已完成")
	private String nodeStatus;

}
