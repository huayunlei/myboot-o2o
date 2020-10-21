package com.ihomefnt.o2o.intf.domain.main.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */
@ApiModel("首页节点")
@Data
@Accessors(chain = true)
public class MainNode {

    @ApiModelProperty("节点Id")
    private Integer nodeId;

    @ApiModelProperty("节点名称")
    private String nodeName;

    @ApiModelProperty("前端ui框架样式Id")
    private Integer uiFrame;

    @ApiModelProperty("前端ui框架样式名称")
    private String uiFrameName;

}
