package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */
@Data
@ApiModel("户型信息")
public class HouseInfo {

    @ApiModelProperty("小区")
    private String community;

    @ApiModelProperty("房号")
    private String houseNo;

    @ApiModelProperty("户型")
    private String houseType;

    @ApiModelProperty("是否完善")
    private Boolean houseTypeCompleted;
}
