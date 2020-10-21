package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "房产扩展信息")
public class HousePropertyInfoExtResultDto {

    @ApiModelProperty(value = "交付日期")
    private String deliverTime;

    @ApiModelProperty(value = "交付状态")
    private Integer deliverStatus;

    @ApiModelProperty(value = "交付状态名称")
    private String deliverStatusStr;

    @ApiModelProperty(value = "家装预算")
    private Integer budget;

    @ApiModelProperty(value = "家装预算名称")
    private String budgetStr;

    @ApiModelProperty(value = "意向风格，多个使用逗号分隔")
    private String style;
    
    @ApiModelProperty(value = "意向风格值，多个使用逗号分隔")
    private String styleStr;

    @ApiModelProperty(value = "购房用途")
    private Integer useType;

    @ApiModelProperty(value = "购房用途名称")
    private String useTypeStr;

    @ApiModelProperty(value = "入住人员")
    private String usePerson;

    @ApiModelProperty(value = "客户来源")
    private Integer customerOrigin;

    @ApiModelProperty(value = "客户来源名称")
    private String customerOriginStr;

    @ApiModelProperty(value = "置业顾问姓名")
    private String adviserName;

    @ApiModelProperty(value = "置家顾问手机号")
    private String adviserMobile;

    @ApiModelProperty(value = "备注")
    private String remark;
}