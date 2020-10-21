package com.ihomefnt.o2o.intf.domain.promotion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by danni.wu on 2018/5/26.
 */
@Data
@ApiModel("订单活动详情")
public class OrderPromotionActDetailDto {

    @ApiModelProperty(value = "有效活动详情")
    private QueryPromotionResultDto promotionResultDto;

    @ApiModelProperty(value = "订单编号")
    private Integer orderNum;
}
