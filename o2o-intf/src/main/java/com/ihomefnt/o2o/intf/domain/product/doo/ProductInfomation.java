package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class ProductInfomation {
	/** 主鍵 */
    private Long productId;
    private String name;
    private String pictureUrlOriginal;
    private double priceCurrent;
    private double priceMarket;
    private String productModel;
    private String serialNo;
    private String standardLong;
    private String standardWidth;
    private String standardHigh;
    private String feature;
    private String brand;
    private String madeIn;
    private String deliveryCity;
    private String graphicDescription;
    private String secondContentsName;
    private Integer status;
    private String productAttrJson;
    private int priceHide;//0表示价格不隐藏，1表示价格隐藏
    private int sales;
    private String productType;

}
