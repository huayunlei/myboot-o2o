package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

@Data
public class TSuitProduct {
    private Long productId;
    private String productName;
    private String productImages;
    private String productFirstImage;
    private String roomName;
    private String suitName;
    private Double suitPrice;
    private Long roomId;
    private Long suitId;
    private Integer productNum = 0; //同一空间下相同商品的数量
    
}
