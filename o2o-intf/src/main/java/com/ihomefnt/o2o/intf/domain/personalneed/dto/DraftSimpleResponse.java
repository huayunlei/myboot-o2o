package com.ihomefnt.o2o.intf.domain.personalneed.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wanyunxin
 * @create 2019-03-08 18:49
 */
@Data
public class DraftSimpleResponse {

    @ApiModelProperty("已选方案总价")
    private BigDecimal totalPrice;
}
