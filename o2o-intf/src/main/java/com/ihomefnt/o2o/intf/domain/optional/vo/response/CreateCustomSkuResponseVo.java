package com.ihomefnt.o2o.intf.domain.optional.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 创建新的sku
 * 响应信息
 *
 * @author liyonggang
 * @create 2018-11-24 16:51
 */
@Data
@ApiModel("新的sku")
public class CreateCustomSkuResponseVo {

    private Integer skuId;//skuId,
    @ApiModelProperty("艾佳价")
    private BigDecimal aijiaPrice;//艾佳价,
    @ApiModelProperty("图片地址")
    private String image;//图片地址,
    @ApiModelProperty(" sku 名称")
    private String skuName;// sku 名称
    @ApiModelProperty("压缩图片")
    private String smallImage;//图片地址,
    @ApiModelProperty("差价")
    private BigDecimal priceDiff;
}
