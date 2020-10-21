package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 代客下单 硬装增项
 *
 * @author ZHAO
 */
@Data
@ApiModel("硬装增项")
public class ValetHardItemInfo implements Serializable {

    @ApiModelProperty("类目说明")
    private String hardItemName;//类目说明

    @ApiModelProperty("数量")
    private Integer hardCount;//数量

    @ApiModelProperty("记录单位")
    private String measure;//记录单位

    public ValetHardItemInfo() {
        this.hardItemName = "";
        this.hardCount = 0;
        this.measure = "";
    }

}
