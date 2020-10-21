package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 硬装子订单信息
 *
 * @author liyonggang
 * @create 2019-02-22 20:05
 */
@Data
public class AppHardOrderInfoDto {


    private Integer hardOrderId;//硬装子订单号,

    private Integer hardOrderStatus;//订单状态，取值：0：待付款，1：待分配，2：待排期，3：待施工，4：施工中，5：已完成，6：已取消,

    private String hardOrderStatusName;//订单状态，取值：0：待付款，1：待分配，2：待排期，3：待施工，4：施工中，5：已完成，6：已取消,

    private BigDecimal hardOrderAmount;// 硬装子订单金额,

    private Integer suitType;// 套装类型 ：0：自由搭配 1：整套,

    private String suitTypeName;// 套装类型 ：0：自由搭配 1：整套,

    private Integer productCount;// 商品总数量
}
