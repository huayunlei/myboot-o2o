package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wanyunxin
 * @create 2019-03-04 16:15
 */
@Data
public class HardProcessSimple {

    @ApiModelProperty("工艺id")
    private Integer processId;

    @ApiModelProperty("使用这种工艺的价格")
    private BigDecimal price;

    @ApiModelProperty("是否默认工艺")
    private Boolean processDefault;

}
