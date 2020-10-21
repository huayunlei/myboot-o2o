package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 增减项信息
 *
 * @author ZHAO
 */
@Data
@ApiModel("增减项信息")
public class IncrementItemInfo implements Serializable {

    @ApiModelProperty("增减项总数")
    private Integer incrementItemTotalCount;//增减项总数

    @ApiModelProperty("合计金额")
    private BigDecimal totalAmount;//合计金额

    @ApiModelProperty("软装增项")
    private List<ValetSoftProductInfo> softProductInfoList;//软装增项

    @ApiModelProperty("硬装增项")
    private List<ValetHardItemInfo> hardChargeInfoList;//硬装增项

    @ApiModelProperty("硬装减项")
    private List<ValetHardItemInfo> hardReductionInfoList;//硬装减项
    public IncrementItemInfo() {
        this.incrementItemTotalCount = 0;
        this.totalAmount = BigDecimal.ZERO;
        this.softProductInfoList = new ArrayList<ValetSoftProductInfo>();
        this.hardChargeInfoList = new ArrayList<ValetHardItemInfo>();
        this.hardReductionInfoList = new ArrayList<ValetHardItemInfo>();
    }
}
