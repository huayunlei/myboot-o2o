package com.ihomefnt.o2o.intf.domain.hbms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * created 2017/8/20
 *
 * @author gaoxin
 */
@Data
@ApiModel(description = "项目详情页项目进度信息")
public class ProjectDetailProgressInfoVo {

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "施工状态 0-准备开工 1-开工交底 2-水电验收 3-瓦木验收 4-竣工验收 5施工完成")
    private Integer projectStatus;

    @ApiModelProperty(value = "施工状态：0-待排期 1-待施工 2-施工中 3-施工完成 4-待分配")
    private Integer listStatus;

    @ApiModelProperty(value = "进度节点列表")
    private List<ProjectDetailProgressNodeVo> nodeList;
}
