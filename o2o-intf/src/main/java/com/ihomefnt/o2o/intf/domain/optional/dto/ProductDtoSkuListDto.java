package com.ihomefnt.o2o.intf.domain.optional.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDtoSkuListDto {

    private Integer id;

    private String skuPvsId;

    private String skuPvs;

    @ApiModelProperty("长")
    private Integer length;// 长,

    @ApiModelProperty("宽")
    private Integer width;// 宽,

    @ApiModelProperty("高")
    private Integer height;// 高,

    List<String> images;

    List<skuExtraFieldDto> skuExtraFields;

    @Data
    public static class skuExtraFieldDto{

        private String skuFieldName;

        private String skuFieldValue;

    }


}
