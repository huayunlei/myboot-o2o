package com.ihomefnt.o2o.intf.domain.sku.vo.response;

import com.ihomefnt.o2o.intf.domain.optional.dto.SkuCraftVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("查询sku返回对象")
public class QuerySkuDetailResponseVo {

    @ApiModelProperty("skuID")
    private Integer skuId;

    @ApiModelProperty("spuName")
    private String spuName;

    @ApiModelProperty("productName")
    private String productName;

    @ApiModelProperty("老类目名")
    private String categoryName;

    @ApiModelProperty("尺寸")
    private String measure;

    @ApiModelProperty("末级类目ID")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty("长")
    private Integer length;

    @ApiModelProperty("宽")
    private Integer width;

    @ApiModelProperty("高")
    private Integer height;

    @ApiModelProperty("长最大值")
    private Integer maxLength;

    @ApiModelProperty("宽最大值")
    private Integer maxWidth;

    @ApiModelProperty("高最大值")
    private Integer maxHeight;

    @ApiModelProperty("长最小值")
    private Integer minLength;

    @ApiModelProperty("宽最小值")
    private Integer minWidth;

    @ApiModelProperty("高最小值")
    private Integer minHeight;

    @ApiModelProperty("艾佳价")
    private BigDecimal aijiaPrice;

    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    @ApiModelProperty("SKU 图片地址,")
    private String imageUrl;

    @ApiModelProperty("多图")
    private List<String> imageUrlList;

    @ApiModelProperty("多图")
    private List<SkuImageDto> images;

    @ApiModelProperty("定制品属性")
    private List<CustomItemDto> customItemList;

    @ApiModelProperty("硬装工艺")
    private List<SkuCraftVo> craftVoList;

    @ApiModelProperty("sku属性集合")
    private List<AttributesDto> attributes;

    @ApiModelProperty("符文本描述内容")
    private String detailContent;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SkuImageDto {
        private String url;
    }

}
