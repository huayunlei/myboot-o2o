package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.List;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class UserProductFavoritesResponse {
	private Long productId;
	private String name;
	private double priceCurrent;
	private double priceMarket;
	private List<String> pictureUrlOriginal;
    private Long status;
    private Integer typeKey;
    
    private String houseName;
    private String styleName;
    private String size;
    private String suitName;
    private String suitCount;
    private String roomCount;
    
    private String roomAttr;
    private String firstContentsName;

	public UserProductFavoritesResponse(
			UserProductFavorites userProductFavorites) {
		this.productId = userProductFavorites.getProductId();
		this.name = userProductFavorites.getName();
		this.priceMarket = userProductFavorites.getPriceMarket();
		this.priceCurrent = userProductFavorites.getPriceCurrent();
		this.status = userProductFavorites.getStatus();
		this.typeKey = userProductFavorites.getTypeKey();
		
		this.houseName = userProductFavorites.getHouseName();
		this.styleName = userProductFavorites.getStyleName();
		this.size = userProductFavorites.getSize();
		this.suitName = userProductFavorites.getSuitName();
		this.suitCount = userProductFavorites.getSuitCount();
		this.roomCount = userProductFavorites.getRoomCount();
	}

	public String getRoomAttr() {
		return String.valueOf(size) + "平方 | " + String.valueOf(roomCount) + "件套 | " + styleName;
	}

	public String getHouseName() {
    	return houseName + " | " + String.valueOf(size) + "平方 | " + String.valueOf(suitCount) + "件套 | " + styleName;
    }
}
