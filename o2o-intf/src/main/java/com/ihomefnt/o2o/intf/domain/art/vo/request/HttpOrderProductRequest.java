package com.ihomefnt.o2o.intf.domain.art.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HttpOrderProductRequest {

	/**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Integer productId;

    @ApiModelProperty("改版本商品id 商品塞skuId，艾商城艺术品塞specificationId")//2019年8月10日
    private String skuId;
    /**
     * 商品数量
     */
    private Integer productCount;
    /**
     * 商品售价
     */
    private BigDecimal productPrice;
    /**
     * 是否样品 
     * 1 样品
     * 0 非样品
     */
    private Integer sample;
    /**
     * 商品备注
     */
    private String remark;

    @ApiModelProperty("定制内容")
    private String customizedContent;

}
