package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 小星星订单
 * Author: ZHAO
 * Date: 2018年10月14日
 */
@Data
public class StarOrderResponse {
	private Integer orderId;//订单ID
	
	private Integer orderType;//订单类型
	
	private String orderNum;//订单编号
	
	private Integer productCount;//商品数量
	
	private BigDecimal totalPrice;//订单总价
	
	private BigDecimal actualPayMent;//实际支付金额
	
	private Integer state;//订单状态
	
	private String stateDesc;//订单状态描述
	
	private Integer productType;//商品品类
	
	private String productTypeDesc;//商品品类描述
	
	List<StarOrderDetailResp> orderDetailRespList; // 商品信息
	
	private boolean commentFlag;//是否已评价 的 true表示已评价
	
	private String receiverName;//收货人姓名
	
	private String receiverTel;//收货人电话
	
	private String receiverAddress;//详细收货地址
	
	private String createDateStr;//下单日期
	
	private String logisticsDesc;//物流信息
}
