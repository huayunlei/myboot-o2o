package com.ihomefnt.o2o.intf.domain.designdemand.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-11-05 16:06
 */
@Data
@ApiModel("设计任务简单信息")
public class DesignDemandSimpleInfo {

    @ApiModelProperty("设计任务草稿ID")
    private String designDemandId;

    @ApiModelProperty("设计任务记录ID")
    private Integer commitRecordId = 0;

    @ApiModelProperty("订单编号")
    private Integer orderId;

    @ApiModelProperty("创建人id")
    private Integer createUserId;

    @ApiModelProperty("1  - 舒克 2  - betaApp")
    private Integer source = 1;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("户型id")
    private Integer houseTypeId;
}
