package com.ihomefnt.o2o.intf.domain.artist.vo.response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 小星星SKU
 *
 * @author ZHAO
 */
@Data
@ApiModel("小星星SKU")
public class AppArtStarSku {

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品图片")
    private String imgUrl;

    @ApiModelProperty("SKUID")
    private Long skuId;

    @ApiModelProperty("SKU名称")
    private String skuName;

    @ApiModelProperty("SKU价格")
    private BigDecimal price;

    @ApiModelProperty("销售属性:以，分隔")
    private String attrStr;
}
