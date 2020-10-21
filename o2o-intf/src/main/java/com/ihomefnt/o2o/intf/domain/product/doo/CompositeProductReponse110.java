package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.List;
@Data
public class CompositeProductReponse110 {
	private Long compositeProductId;// product id
	private String name;
	private Double price;
	private String saleOff;
    private Boolean isShowSaleOff;//是否展示价格折扣
	private String designFeatures;
	private String experienceAddress;
	private String summary;
	private List<String> pictureUrlOriginal;
	private Double latitude;
	private Double longitude;
	public CompositeProductReponse110(HouseSuitProduct houseSuitProduct) {
		this.compositeProductId=houseSuitProduct.getCompositeProductId();
		this.name=houseSuitProduct.getName();
		this.price=houseSuitProduct.getPrice();
		this.summary=houseSuitProduct.getHouseName()+"|"+houseSuitProduct.getHouseSize()+"|"+houseSuitProduct.getStyle();
		this.saleOff=houseSuitProduct.getDeal().toString();
        this.isShowSaleOff = houseSuitProduct.getIsShowDeal();
		this.pictureUrlOriginal=houseSuitProduct.getPictureUrlOriginal();
	}
	public CompositeProductReponse110(SuitProduct110 suitProduct110) {
		this.compositeProductId=suitProduct110.getCompositeProductId();
		this.name=suitProduct110.getName();
		this.price=suitProduct110.getPrice();
		this.summary=suitProduct110.getSummary();
		this.saleOff=suitProduct110.getSaleOff();
        this.isShowSaleOff = suitProduct110.getIsShowDeal();
		this.pictureUrlOriginal=suitProduct110.getPictureUrlOriginalList();
	}
}
