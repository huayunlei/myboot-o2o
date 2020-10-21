/*
 * Author: ZHAO
 * Date: 2018/10/11
 * Description:ArtStarGood.java
 */
package com.ihomefnt.o2o.intf.domain.artist.vo.response;

import com.ihomefnt.cms.intf.art.dto.ArtImage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 小星星商品
 *
 * @author ZHAO
 */
@Data
@ApiModel("小星星商品")
public class ArtStarGood {

    @ApiModelProperty("商品ID")
    private Long artId;

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("商品名称")
    private String artName;

    @ApiModelProperty("商品图片")
    private String imgUrl;

    @ApiModelProperty("SKU列表")
    private List<ArtStarSku> skuList;
    
    @ApiModelProperty("图文描述")
    private List<ArtImage> imageList;

    @ApiModelProperty("销售属性")
    private List<ArtSaleAttr> saleAttrList;

    @ApiModelProperty("最低价格")
    private BigDecimal minPrice;

    @ApiModelProperty("最高价格")
    private BigDecimal maxPrice;

    @ApiModelProperty("是否配文:1可配文,0不可配")
    private Integer matchedFlag;
}
