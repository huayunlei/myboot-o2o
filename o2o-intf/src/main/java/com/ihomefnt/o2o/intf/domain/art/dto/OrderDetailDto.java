package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderDetailDto implements Serializable{

	private static final long serialVersionUID = -2116719331699532558L;

	/**
     *订单明细id
     */
    private Integer idtDetail;

    /**
     *订单id
     */
    private Integer fidOrder;

    /**
     *套装id
     */
    private Integer fidSuit;

    /**
     *空间id
     */
    private Integer fidSuitRoom;

    /**
     *商品id
     */
    private Integer fidProduct;

    /**
     *商品数量
     */
    private Integer productAmount;

    /**
     *商品单价
     */
    private BigDecimal productPrice;

    /**
     *商品总价
     */
    private BigDecimal productAmountPrice;

    /**
     *厂家编号
     */
    private String factoryNoVersion;

    /**
     *订单的商品状态
     */
    private Integer state;

    /**
     *是否是样品
     */
    private Integer sample;

    /**
     *备注
     */
    private String remark;

}
