package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

@Data
public class TProduct {

    private Long productId;

    private String productName;

    private double productPrice;//艾佳售价

    private double productMarketPrice;//市场价

    private String productOrigin;//产品产地

    private String productImages;//商品图片
    
    private String firstImage;//商品头图

    private Integer count;//商品数量
//长宽高
    private Float length;
    private Float width;
    private Float height;

    private String material;//材料

    private String brand;//品牌
    private Long brandId;//品牌id

}
