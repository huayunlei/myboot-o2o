package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.List;

/**
 * Created by shirely_geng on 15-1-17.
 */
@Data
public class CompositeProductReponse {

    private Long compositeProductId;// product id
    private String name;
    private Double price;
    private String saleOff;
    private String designFeatures;
    private String experienceAddress;
    private String summary;
    private List<String> pictureUrlOriginal;// bean array
    private double latitude;
    private double longitude;

    public CompositeProductReponse(CompositeProduct compositeProduct) {
        this.compositeProductId = compositeProduct.getCompositeProductId();
        this.name = compositeProduct.getName();
        this.price = compositeProduct.getPrice();
        this.saleOff = compositeProduct.getSaleOff().toString();
        this.designFeatures = compositeProduct.getDesignFeatures();
        this.experienceAddress = compositeProduct.getExperienceAddress();
        this.summary = compositeProduct.getSummary();
        this.latitude = compositeProduct.getLatitude();
        this.longitude = compositeProduct.getLongitude();
    }
}
