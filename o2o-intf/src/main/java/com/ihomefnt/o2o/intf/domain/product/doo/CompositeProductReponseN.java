package com.ihomefnt.o2o.intf.domain.product.doo;

import com.ihomefnt.o2o.intf.domain.suit.dto.RoomImage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class CompositeProductReponseN {


    private Long compositeProductId;// product id
    private String name;
    private Double price;
    private Double singlePrice;//单品价
    private Long sales;//销量
    private Integer commentCount;//评论数
    private Integer consultCount;//咨询数
    private String saleOff;
    private Boolean isShowSaleOff;//是否展示价格折扣
    private String designFeatures;
    private String brief;   
    private String experienceAddress;
    private String summary;
    private List<String> pictureUrlOriginal;// bean array
    private List<RoomImage> pictureUrlOriginalList;// bean array
    private double latitude;
    private double longitude;
    private String designerImg;
    private String fullView3d;
    private String designerUrl;//设计师首页的url

    private String designerName;
    
    private List<Room> roomList;
    
    private String summary2;
    private Integer size;
    private String style;
    private Integer count;
    
    public CompositeProductReponseN(CompositeProduct compositeProduct) {
        this.compositeProductId = compositeProduct.getCompositeProductId();
        this.name = compositeProduct.getName();
        this.price = compositeProduct.getPrice();
        this.saleOff = compositeProduct.getSaleOff();
        this.isShowSaleOff = compositeProduct.getIsShowDeal();
        this.designFeatures = compositeProduct.getDesignFeatures();
        this.experienceAddress = compositeProduct.getExperienceAddress();
        this.summary = compositeProduct.getSummary();
        this.latitude = compositeProduct.getLatitude();
        this.longitude = compositeProduct.getLongitude();
        this.designerImg=compositeProduct.getDesignerImg();
        this.designerName=compositeProduct.getDesignerName();
        this.summary2 = compositeProduct.getSummary2();
        this.size = compositeProduct.getSize();
        this.style = compositeProduct.getStyle();
        this.count = compositeProduct.getCount();
        this.fullView3d = compositeProduct.getFullView3d();
        this.sales = compositeProduct.getSaleCount();
        this.brief=compositeProduct.getBrief();
    }
}
