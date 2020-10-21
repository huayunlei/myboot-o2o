package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ArtOrderVo {
    private Long orderNo;

    private Integer orderId;

    private BigDecimal orderAmount;

    @ApiModelProperty("0未支付 1已支付")
    private Integer orderState;

}
