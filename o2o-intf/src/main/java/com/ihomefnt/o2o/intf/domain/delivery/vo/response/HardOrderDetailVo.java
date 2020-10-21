package com.ihomefnt.o2o.intf.domain.delivery.vo.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HardOrderDetailVo {

    @ApiModelProperty("施工订单id")
    private String hardOrderId;

    @ApiModelProperty("施工单状态 0-准备开工 1-开工交底 2-水电验收 3-客户自施工项目阶段 4-瓦木验收 5-竣工验收 6-施工完成")
    private Integer hardStatus;

    @ApiModelProperty("施工阶段名称")
    private String hardStatusName;

    @ApiModelProperty("计划开工日期")
    private String planBeginDate;

    @ApiModelProperty("实际开工日期")
    private String actualBeginDate;

    @ApiModelProperty("计划完工日期")
    private String planEndDate;

    @ApiModelProperty("实际完工日期")
    private String actualEndDate;

    @ApiModelProperty("开工天数")
    private Integer days;

    @ApiModelProperty("艾管家姓名")
    private String surveyorName = "";

    @ApiModelProperty("艾管家手机号")
    private String surveyorPhone = "";

    @ApiModelProperty("开工天数单位")
    private String daysUnit;

}
