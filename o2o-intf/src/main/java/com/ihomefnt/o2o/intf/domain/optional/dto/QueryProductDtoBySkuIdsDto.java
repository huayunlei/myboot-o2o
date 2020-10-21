package com.ihomefnt.o2o.intf.domain.optional.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryProductDtoBySkuIdsDto {

    private Integer skuId;

    private String categoryName;

    private String productName;

    private Integer lastCategoryId;

    private String lastCategoryName;

    private Integer type;// sku 类型

    private String brandName;// 品牌名

    private String styleName;// 风格

    private String itemSize;// 尺寸规格

    private String materialDesc;

    private String originName;// 产地

    private List<String> images;

    private List<SkuCraftVo> craftVoList;

    /**
     * 符文本描述内容
     */
    private String graphicDetails;

    @ApiModelProperty("sku属性集合")
    private List<SkuPropertyDto> propertyList;

    private List<ProductDtoSkuListDto> skuList;

    private List<ProductSkuPropertyDto> productSkuPropertyList;

    private List<ProductSpuPropertyDto> productSpuPropertyList;

    private CustoOptionalResponseDto attrTreeRes;

}
