package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-03-21 11:35
 */
@ApiModel("创建订单返回")
@Data
public class CreateOrderResponse {

    @ApiModelProperty("订单编号")
    private Integer orderId;
    @ApiModelProperty("房产id 已经废弃，新版本使用customerHouseId")
    @Deprecated
    private Integer houseId;
    @ApiModelProperty("房产id")
    private Integer customerHouseId;//房产id,
    @ApiModelProperty("下单金额")
    private BigDecimal contractAmount;
    @ApiModelProperty("保价天数")
    private Integer downCount =30;
    @ApiModelProperty("已签约说明")
    private List<String> signInfoList;
    @ApiModelProperty("订单是否交完全款 0否  1是")
    private Integer allMoney = 0;
}
