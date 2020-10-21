package com.ihomefnt.o2o.intf.domain.right.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wanyunxin
 * @create 2019-05-09 14:34
 */
@ApiModel("权益及订单信息")
@Data
@AllArgsConstructor
public class RightsAndOrderResponse {

    @ApiModelProperty("等级")
    private Integer gradeId ;

    @ApiModelProperty("艾升级券面值")
    private BigDecimal upGradeCouponAmount;
}
