package com.ihomefnt.o2o.intf.domain.homepage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */

@Data
@ApiModel("订单节点")
public class OrderNode {

    @ApiModelProperty("节点id")
    private Integer nodeId;

    @ApiModelProperty("节点名称")
    private String nodeName;

    @ApiModelProperty("节点完成度")
    private BigDecimal completeRate;

    @ApiModelProperty("节点时态")
    private Integer status;
}
