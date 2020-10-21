package com.ihomefnt.o2o.intf.domain.optional.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 新订制品属性返回
 *
 * @author liyonggang
 * @create 2018-11-24 14:59
 */
@Data
@ApiModel("新订制品信息")
public class CustoOptionalResponseVo {

    private Integer spuId;//spuId,
    private Integer skuId;//skuId,
    private String spuName;//spuName,
    private String skuName;// skuName,
    @ApiModelProperty("长")
    private Integer length;// 长,
    @ApiModelProperty("宽")
    private Integer width;// 宽,
    @ApiModelProperty("高")
    private Integer height;// 高,
    @ApiModelProperty("长最大值")
    private Integer maxLength;// 长最大值,
    @ApiModelProperty("宽最大值")
    private Integer maxWidth;// 宽最大值,
    @ApiModelProperty("高最大值")
    private Integer maxHeight;//高最大值,
    @ApiModelProperty("长最小值")
    private Integer minLength;// 长最小值,
    @ApiModelProperty("宽最小值")
    private Integer minWidth;// 宽最小值,
    @ApiModelProperty("高最小值")
    private Integer minHeight;// 高最小值,
    @ApiModelProperty("艾佳价")
    private BigDecimal aijiaPrice;//艾佳价,
    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;//采购价,
    @ApiModelProperty("SKU 图片地址,")
    private String imageUrl;//SKU 图片地址,
    private List<CustomItemResponseVo> customItemList;//定制项数组
    private String measure;//尺寸

    public String getMeasure() {
        return length+"mm x "+width+"mm x "+height+"mm";
    }
}
