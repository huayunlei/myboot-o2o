package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class ProductInfomationResponse {
	/** 主鍵 */
	private Long productId;
	private String name;
	private List<String> pictureUrlOriginal;
	private double priceCurrent;
	private double priceMarket;
	private String productModel;
	private String standardLong;
	private String standardWidth;
	private String standardHigh;
	private String feature;
	private String brand;
	private String madeIn;
	private String deliveryCity;
	private String graphicDescription;
	private String firstContentsName; //TODO: this should be removed in next iteration
	private String secondContentsName; //TODO: this should be removed in next iteration
	private Integer status;
	private List<String> detailList;
    private List<ProductDetail> mainDetails = new ArrayList<ProductDetail>();//主要参数
    private List<ProductDetail> adnexalDetails;//附加参数 2.0之前版本
    private List<ProductDetailGroup> productDetailGroups;//属性 2.0以后版本
    

	private int priceHide;//0表示价格不隐藏，1表示价格隐藏
    private int sales;
    private String productType;

	public ProductInfomationResponse(ProductInfomation product) {
		this.productId = product.getProductId();
		this.name = product.getName();
		this.priceCurrent = product.getPriceCurrent();
		this.priceMarket = product.getPriceMarket();
		this.productModel = product.getProductModel()+product.getSerialNo();//前台要求特殊处理
		this.standardLong = product.getStandardLong();
		this.standardWidth = product.getStandardWidth();
		this.standardHigh = product.getStandardHigh();
		this.feature = product.getFeature();
		this.brand = product.getBrand();
		this.madeIn = product.getMadeIn();
		this.deliveryCity = product.getDeliveryCity();
		this.graphicDescription = product.getGraphicDescription();
        this.secondContentsName = product.getSecondContentsName();
        this.status = product.getStatus();
        this.priceHide = product.getPriceHide();
        this.sales = product.getSales();
        this.productType = product.getProductType();
        //1.规格 2.特点 3.品牌  4.产地
        if (standardLong != null && standardWidth != null && standardHigh != null) {
            ProductDetail specifications = new ProductDetail();
            specifications.setDetailKey("规格");
            specifications.setDetailValue(standardLong + "*" + standardWidth + "*" + standardHigh
                    + "mm(长*宽*高)");
            this.mainDetails.add(specifications);
        }
        if (product.getFeature() != null && product.getFeature().trim().length() > 0) {
            ProductDetail feature = new ProductDetail();
            feature.setDetailKey("特点");
            feature.setDetailValue(product.getFeature());
            this.mainDetails.add(feature);
        }
        if (product.getBrand() != null && product.getBrand().trim().length() > 0) {
            ProductDetail brand = new ProductDetail();
            brand.setDetailKey("品牌");
            brand.setDetailValue(product.getBrand());
            this.mainDetails.add(brand);
        }
        if (product.getMadeIn() != null && product.getMadeIn().trim().length() > 0) {
            ProductDetail madeIn = new ProductDetail();
            madeIn.setDetailKey("产地");
            madeIn.setDetailValue(product.getMadeIn());
            this.mainDetails.add(madeIn);
        }

	}

}
