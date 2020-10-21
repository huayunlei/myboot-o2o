package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("节点详情")
@Data
public class NodeDetailVo {

    @ApiModelProperty("节点id")
    private Long nodeId;

    @ApiModelProperty("节点名")
    private String nodeName;

    @ApiModelProperty("艾管家节点验收状态 0 未完成 1 已完成")
    private Integer surveyorCheckStatus;

    @ApiModelProperty("客户节点验收状态 0-未验收；1-不通过；2-已通过")
    private Integer ownerCheckStatus;

    @ApiModelProperty("计划开工日期")
    private String planBeginDate;

    @ApiModelProperty("实际开工日期")
    private String actualBeginDate;

    @ApiModelProperty("计划完工日期")
    private String planEndDate;

    @ApiModelProperty("实际完工日期")
    private String actualEndDate;

    @ApiModelProperty("计划工期")
    private Integer planDays;

    @ApiModelProperty("开工天数")
    private Integer days;

    @ApiModelProperty("节点描述")
    private String nodeDesc;

    @ApiModelProperty("节点验收记录")
    private List<NodeProcessRecordVo> nodeProcessRecordVos;

    @ApiModelProperty("客户不满意时的评价内容")
    private String comment;

    @ApiModelProperty("节点验收")
    private List<NodeProcessRecordVoV2> checkNodeVos;

    @ApiModelProperty("开工天数单位")
    private String daysUnit;

    @ApiModelProperty("计划工期单位")
    private String planDaysUnit;

}
