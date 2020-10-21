package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.HouseSuitProduct;
import lombok.Data;

@Data
public class HttpHouseSuitProductResponse {
    private Long houseId;//房型
    private String summary;//屋型概述
    private String title;//套装标题
    private Double lowestPrice;//最低价格
    private Double highestPrice;//最高价格
    private String saleOff;//价格折扣
    private Boolean isShowSaleOff;//是否展示价格折扣
    private Long compositeProductId;//套装id
    private List<String> pictureUrlOriginal;// 套装图片

    public HttpHouseSuitProductResponse(HouseSuitProduct houseSuitProduct) {
        this.houseId = houseSuitProduct.getHouseId();
        this.summary = houseSuitProduct.getSummary();
        this.title = houseSuitProduct.getName();
        this.lowestPrice = houseSuitProduct.getLowestPrice();
        this.highestPrice = houseSuitProduct.getHighestPrice();
        this.saleOff = houseSuitProduct.getDeal().toString();
        this.isShowSaleOff = houseSuitProduct.getIsShowDeal();
        this.pictureUrlOriginal = houseSuitProduct.getPictureUrlOriginal();
        this.compositeProductId = houseSuitProduct.getCompositeProductId();
    }
}
