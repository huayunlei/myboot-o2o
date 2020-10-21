package com.ihomefnt.o2o.intf.domain.product.doo;


import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class ProductSummary {
    private Long productId;//商品id
    private String name;//商品名称
	private String pictureUrlOriginal;//商品图片
	private double priceCurrent;//当前价格
    private double priceMarket;//市场价
    private String firstContentsName;//房间名称
    private String roomType;//房间类型
    private String secondContentsName;//房间里的商品名称
    private Integer  productCount;//商品的数量
    private double roomPrice;//房间价格
    private Integer status;
    private Integer typeKey;
    private Long roomId;
    private int priceHide;//0表示价格不隐藏，1表示价格隐藏
    private String productAttrJson;
    
    @JsonIgnore(value = true)
    private String roomImages;//房间图片
}
