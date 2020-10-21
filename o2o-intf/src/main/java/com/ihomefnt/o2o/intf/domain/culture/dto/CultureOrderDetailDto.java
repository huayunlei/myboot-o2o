package com.ihomefnt.o2o.intf.domain.culture.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class CultureOrderDetailDto implements Serializable {
	private static final long serialVersionUID = 1L;

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
    @NotNull(message="商品id不能为空")
    private Integer fidProduct;

    /**
     *商品数量
     */
    @NotNull(message="商品数量不能为空")
    private Integer productAmount;

    /**
     *商品单价
     */
    @NotNull(message="商品单价不能为空")
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
