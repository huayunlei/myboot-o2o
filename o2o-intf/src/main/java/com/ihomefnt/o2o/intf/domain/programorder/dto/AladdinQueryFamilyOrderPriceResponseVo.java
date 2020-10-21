package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 查询订单价格返回值
 * @author xwf
 *
 */
@ApiModel("查询订单价格返回值")
@Data
public class AladdinQueryFamilyOrderPriceResponseVo {
    private Integer orderNum;//订单编号

    private Integer houseId;//房产id,

    private BigDecimal contractAmount;//订单金额

    private Integer code;//响应码

}
