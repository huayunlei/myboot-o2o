package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 软装详情
 *
 * @author liyonggang
 * @create 2019-02-22 20:12
 */
@Data
public class AppValetOrderInfoSoftDetailDto {


    private Integer skuId;//skuId,

    private String productImage;//图片路径,

    private String productName;//商品名称,

    private Integer productCount;//商品数量,

    private Integer productStatus;//商品状态,

    private String productStatusStr;//商品状态字符串,

    private String specifications;//商品规格,

    private String material;//材质,

    private BigDecimal length;//长,

    private BigDecimal width;//宽,

    private BigDecimal height;//高,

    private String brandAndSeries;//品牌&系列名称,

    private Integer furnitureType;//商品类型,

    private String superKey;//唯一标识
}
