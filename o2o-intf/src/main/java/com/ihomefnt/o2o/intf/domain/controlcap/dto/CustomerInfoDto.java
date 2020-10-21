package com.ihomefnt.o2o.intf.domain.controlcap.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("客户信息")
public class CustomerInfoDto {

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phoneNumber;

    /**
     * 楼盘项目名称
     */
    @ApiModelProperty("楼盘项目名称")
    private String buildingName;

    /**
     * 楼盘ID
     */
    @ApiModelProperty("楼盘ID")
    private Integer buildingId;

    /**
     * 分区名
     */
    @ApiModelProperty("分区名")
    private String partitionName;

    /**
     * 分区ID
     */
    @ApiModelProperty("分区ID")
    private Integer zoneId;

    /**
     * 楼栋号
     */
    @ApiModelProperty("楼栋号")
    private String housingNum;

    /**
     * 单元号
     */
    @ApiModelProperty("单元号")
    private String unitNum;

    /**
     * 房间号
     */
    @ApiModelProperty("房间号")
    private String roomNum;

    /**
     * 户型名称
     */
    @ApiModelProperty("户型名称")
    private String houseTypeName;

    /**
     * 户型ID
     */
    @ApiModelProperty("户型ID")
    private Integer houseTypeId;

    /**
     * 面积
     */
    @ApiModelProperty("面积")
    private String size;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}