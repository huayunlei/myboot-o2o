package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 硬装施工信息
 *
 * @author ZHAO
 */
@Data
@ApiModel("硬装施工信息")
public class HardConstructInfo implements Serializable {

    @ApiModelProperty("订单id")
    private Integer hardOrderId;//订单id

    @ApiModelProperty("hbms硬装订单状态")
    private Integer hardOrderStatus;// hbms硬装订单状态

    @ApiModelProperty("hbms硬装订单状态字符串")
    private String hardOrderStatusStr;// hbms硬装订单状态字符串

    @ApiModelProperty("施工时间")
    private String constructTime;//施工时间

    public HardConstructInfo() {
        this.hardOrderId = -1;
        this.hardOrderStatus = 0;
        this.hardOrderStatusStr = "";
        this.constructTime = "";
    }
}
