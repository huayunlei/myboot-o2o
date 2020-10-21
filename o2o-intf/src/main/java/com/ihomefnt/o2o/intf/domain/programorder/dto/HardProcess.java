package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/22
 */

@ApiModel("硬装工艺")
@Data
public class HardProcess implements Serializable{

    @ApiModelProperty("工艺id")
    private Integer processId;

    @ApiModelProperty("工艺名称")
    private String processName;

    @ApiModelProperty("使用这种工艺的价格")
    private BigDecimal price;

    @ApiModelProperty("工艺示意图")
    private String processImage;

    @ApiModelProperty("工艺缩略图")
    private String smallImage;

    @ApiModelProperty("是否默认工艺")
    private Boolean processDefault;

    @ApiModelProperty("差价")
    private BigDecimal priceDiff;

    @ApiModelProperty("子sku")
    private List<HardItemSelection> hardItemSelection;

}
