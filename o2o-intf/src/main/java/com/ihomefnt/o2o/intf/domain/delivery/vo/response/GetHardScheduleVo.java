package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class GetHardScheduleVo {

    @ApiModelProperty("开工日期")
    private String startDate;

    @ApiModelProperty("完工日期")
    private String endDate;

    @ApiModelProperty("工期")
    private Integer days;

    @ApiModelProperty("计划说明")
    private String scheduleDesc;

    @ApiModelProperty("开工交底")
    private NodeScheduleVo startScheduleVo;

    @ApiModelProperty("水电阶段-开始")
    private NodeScheduleVo hydropowerStart;

    @ApiModelProperty("水电阶段-用户验收")
    private NodeScheduleVo hydropowerEnd;

    @ApiModelProperty("自施工项-用户验收")
    private NodeScheduleVo customerEnd = new NodeScheduleVo();

    @ApiModelProperty("自施工项-开始")
    private NodeScheduleVo customerStart = new NodeScheduleVo();

    @ApiModelProperty("瓦木阶段-瓦木开始")
    private NodeScheduleVo woodenStart;

    @ApiModelProperty("瓦木阶段 - 用户验收")
    private NodeScheduleVo woodenEnd;

    @ApiModelProperty("完工阶段-完工开始")
    private NodeScheduleVo completeStart;

    @ApiModelProperty("软装运输-软装到货")
    private NodeScheduleVo sendSoftEnd;

    @ApiModelProperty("软装安装-完成安装")
    private NodeScheduleVo installSoftEnd;

    @ApiModelProperty("终验阶段-客户验收")
    private NodeScheduleVo finalEnd;

    @ApiModelProperty("交付单状态    0-待交付 1-需求确认 2-交付准备 3交付中 6竣工 7质保 8已完成")
    private Integer deliverStatus;

    @ApiModelProperty(value = "休假时间列表(yyyy-MM-dd)")
    @JsonIgnore
    private List<String> holidays;

    @ApiModelProperty("休假日期")
    private NodeScheduleListResponse holidaysResponse;

    @ApiModelProperty("工期单位")
    private String daysUnit = "";
}
