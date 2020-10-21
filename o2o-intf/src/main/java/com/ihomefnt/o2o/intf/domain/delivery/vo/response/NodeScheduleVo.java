package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class NodeScheduleVo {

    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("节点名")
    private String nodeName;

    @ApiModelProperty("节点描述")
    private String nodeDesc;

}
