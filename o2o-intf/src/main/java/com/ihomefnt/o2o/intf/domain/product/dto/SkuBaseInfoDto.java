package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class SkuBaseInfoDto {

    /**
     * skuid
     */
    private Integer id;

    /**
     * skuid
     */
    private Integer skuId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * pvs 逗号分割(;)
     */
    private String pvsId;

    /**
     * pvs 逗号分割(;)
     */
    private String pvs;

    /**
     * 一级类目id
     */
    private Integer rootCategoryId;

    /**
     * 一级类目名称
     */
    private String rootCategoryName;

    /**
     * 四级类目id
     */
    private Integer categoryId;

    /**
     * 四级类目名称
     */
    private String categoryName;

    /**
     * 末级类目ID
     */
    private Integer lastCategoryId;

    /**
     * 末级类目名称
     */
    private String lastCategoryName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 一级品牌名称
     */
    private String topBrandName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 艾佳售价
     */
    private BigDecimal aijiaPrice;

    /**
     * 市场价（原价）
     */
    private BigDecimal marketPrice;

    /**
     * 定制SKU 售卖单价
     */
    private BigDecimal salePriceSquareMeters;

    /**
     * 定制货物 采购单价
     */
    private BigDecimal purchasePriceSquareMeters;

    /**
     * 颜色
     */
    private String color;

    /**
     * 材质
     */
    private String material;

    /**
     * 尺寸
     */
    private String size;

    /**
     * 长
     */
    private Integer length;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 状态。0、成品。1、定制
     */
    private Integer type;

    private String afterSize;

    private Integer hasXiShe = 0;

    private Integer addUserId;

    /**
     * 用户类别， 0.艾佳。1.喜社
     */
    private Integer userType;

    private Boolean showPurchasePrice;

    /**
     * 二级类目名
     */
    private Integer secondCategoryId;

    /**
     * 二级类目名
     */
    private String secondCategoryName;

    /**
     * 三级类目名
     */
    private Integer thirdCategoryId;

    /**
     * 三级类目名
     */
    private String thirdCategoryName;

    /**
     * 计价单位
     */
    private String chargeUnit;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 系列id
     */
    private Integer seriesId;

    /**
     * 风格id
     */
    private Integer styleId;

    /**
     * 风格名称
     */
    private String styleName;

    /**
     * 规格信息
     */
    private String ruleSize;

    /**
     * 0 不是补件 1 是补件
     */
    private Integer additionalFlag;

    /**
     * 0 未停产 1 已停产
     */
    private Integer suspendedFlag;

    /**
     * sku扩展信息
     */
    private List<SkuExtPropertyInfo> propertyExtList;

    /**
     * sku扩展信息
     */
    @Data
    public static class SkuExtPropertyInfo {
        private Integer id;
        private Integer templateId;
        private Integer productId;
        private Integer skuId;
        private Integer templatePropertyId;
        private Integer propertyId;
        private String propertyName;
        private String propertyValue;
        private Integer propertyValueId;
        private String propertyType;
        private Integer type;
    }

}
