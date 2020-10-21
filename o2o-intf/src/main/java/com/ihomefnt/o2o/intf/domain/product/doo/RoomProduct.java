package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

@Data
public class RoomProduct {
	private Long suitId;
	private String suitName;//房间名称
	private Double suitPrice;//套装价格
	private Long roomId;
	private String roomName;//房间名称
	private Long productId;
	private String productName;//商品名称
	private Integer productCount;
	private Double productPrice;
	private String firstImage;
	private String images;
}
