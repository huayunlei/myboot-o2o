package com.ihomefnt.o2o.intf.domain.product.doo;


import lombok.Data;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class UserProductFavorites {
    private Long productId;
    private String name;
	private String pictureUrlOriginal;
	private double priceCurrent;
    private double priceMarket;
    private Long status;
    private Integer typeKey;
    
    private String houseName;
    private String styleName;
    private String size;
    private String suitName;
    private String suitCount;
    private String roomCount;

}
