package com.ihomefnt.o2o.intf.domain.hbms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created 2017/8/20
 *
 * @author gaoxin
 */
@Data
@ApiModel(description = "项目详情页项目进度节点")
public class ProjectDetailProgressNodeVo {

    @ApiModelProperty(value = "节点名称")
    private String name;

    @ApiModelProperty(value = "节点状态：0未完结，1进行中，2已完结")
    private Integer status;

    @ApiModelProperty(value = "完结人")
    private String completePerson;

    @ApiModelProperty(value = "完结时间")
    private String completeTime;
}
