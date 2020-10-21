package com.ihomefnt.o2o.intf.domain.promotion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 28360 on 2017/9/18.
 */
@Data
@ApiModel("参加活动返回")
public class JoinPromotionResultDto {

    @ApiModelProperty("总优惠金额")
    private BigDecimal promotionAmount;
}
