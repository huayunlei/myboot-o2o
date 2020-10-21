package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 软装信息
 *
 * @author liyonggang
 * @create 2019-02-22 20:03
 */
@Data
public class AppSoftOrderInfoDto {

    private Integer softOrderId;//: 软装子订单号,

    private Integer softOrderStatus;//: 订单状态，取值：0：待付款，1：待采购，2：采购中，3：待配送，4：配送中，5：已完成，6：已取消,

    private String softOrderStatusName;//: 订单状态，取值：0：待付款，1：待采购，2：采购中，3：待配送，4：配送中，5：已完成，6：已取消,

    private BigDecimal softOrderAmount;//(number, optional): 软装总价,

    private Integer suitType;//: 套装类型 ：0：自由搭配 1：整套,

    private String suitTypeName;//: 套装类型 ：0：自由搭配 1：整套,

    private Integer productCount;//: 订单商品总数量
}
