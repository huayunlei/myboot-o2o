package com.ihomefnt.o2o.intf.domain.cart.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

@Data
public class ShoppingCartProductDto {

	private Long productId;
	private String name;
	private List<String> pictureUrlOriginal;
	private double priceCurrent;
	private double priceMarket;
	private Integer typeKey;
	
	@JsonIgnore
	private String imagesUrl;
}
