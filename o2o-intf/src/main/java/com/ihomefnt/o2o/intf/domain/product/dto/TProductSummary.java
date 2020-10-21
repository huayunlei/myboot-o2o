package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TProductSummary implements Serializable{

	private static final long serialVersionUID = -6397880630277208793L;
	private Long productId;
    private String productName;
    private Integer productType;
    private String productTypeName;
    private Double productPrice;//艾佳售价
    private Double productMarketPrice;//市场价
    private String productOrigin;//产品产地
    private String model;//型号
    private String feature;//功能特点
    private String images;//图片
    private String firstImage;
    private Integer status; //商品状态 -1 删除 0 已下架 1 上架
    private Integer priceHide; //0 显示 1 隐藏价格
    private Double priceRatio;
	private Long brandId;//品牌ID
    private String brandName;   //品牌名称
    private Long styleId;   //风格ID
    private String styleName;//风格名称
    private Long materialId;    //材质ID
    private String materialName; //材质
}
