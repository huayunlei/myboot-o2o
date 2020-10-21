package com.ihomefnt.o2o.intf.domain.ordersnapshot.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单快照商品信息
 * @author ZHAO
 */
@Data
public class OrderSnapshotProductResponse {
	private Integer productId;  //商品id
	private Integer productCategoty;  //商品类型
	private Integer productStyle;  //商品风格
	private Integer productBrand;  //商品品牌
    /**
     * 
     * 预计发货时间的倒计时[待发货状态的艺术品订单才有的]
     */
    private Integer deliveryTime;
	private String productImage;  //商品头图
	private String productName;  //商品名称
	private BigDecimal productPrice;  //商品单价
	private Integer productState;  // 商品状态（1.上架 0.下架 -1.删除）
	private Integer amount;  //商品数量
	private String description;  //商品描述
	private List<OrderSnapshotCargoResponse> cargoInfos;  //货物信息
}
