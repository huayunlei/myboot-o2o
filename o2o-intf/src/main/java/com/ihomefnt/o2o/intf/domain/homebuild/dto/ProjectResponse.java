package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
@Data
@ApiModel("项目楼盘返回Vo")
public class ProjectResponse implements Serializable {

    private static final long serialVersionUID = 3677745864848149572L;

    @ApiModelProperty("项目ID")
    private Long buildingId;

    @ApiModelProperty("项目名称")
    private String buildingName;

    @ApiModelProperty("开发商名称")
    private String developer;

    @ApiModelProperty("开发商ID")
    private Integer developerId;

    @ApiModelProperty("省市区,格式:省id-市id-区/县id")
    private String buildingAddress;

    @ApiModelProperty("街道")
    private String street;

    @ApiModelProperty("开发商联系电话")
    private String phone;

    @ApiModelProperty("纬度")
    private BigDecimal latitude;

    @ApiModelProperty("经度")
    private BigDecimal longitude;

    @ApiModelProperty("区县外键")
    private Integer fidDistrict;

    @ApiModelProperty("项目负责人id")
    private Integer managerId;

    @ApiModelProperty("项目负责人电话")
    private String managerMobile;

    @ApiModelProperty("项目负责人名称")
    private String manager;

    @ApiModelProperty("所属公司ID")
    private Integer companyCode;

    @ApiModelProperty("所属公司名称")
    private String companyName;

    @ApiModelProperty("合作类型:1.正式合作 2.临时合作")
    private Integer cooperationType;

    @ApiModelProperty("所属战区ID:0.东部战区 1.中西部战区")
    private Integer warZoneId;

    @ApiModelProperty("所属纵队负责人ID")
    private Integer teamManagerId;

    @ApiModelProperty("纵队负责人")
    private String teamManager;

    @ApiModelProperty("分区信息")
    private List<PartitionVo> partitionVos;

}
