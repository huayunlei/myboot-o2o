package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("字段信息")
@Data
public class NodeStatusVo {

    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("节点名称")
    private String nodeName;

    @ApiModelProperty("0 未开始 1 进行中 2 已完成")
    private Integer status;

    @ApiModelProperty("节点备注")
    private String remark;

}
