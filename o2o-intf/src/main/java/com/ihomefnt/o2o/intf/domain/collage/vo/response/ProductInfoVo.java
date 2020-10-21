package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/15 9:53
 */
@Data
@ApiModel("ProductInfoVo 商品信息")
public class ProductInfoVo {

    @ApiModelProperty("skuId")
    private Integer skuId =10;

    @ApiModelProperty("商品名称")
    private String skuName;

    @ApiModelProperty("skuUrl 商品首图")
    private String skuUrl="/homecard/image/123.jpg";

    @ApiModelProperty("品牌url")
    private String brandUrl="/homecard/iamge/234.jpg";

    @ApiModelProperty("品牌名称")
    private String brandName="55品牌";

    @ApiModelProperty("商品描述")
    private String productDesc="这是一个300ml的保温杯";

    @ApiModelProperty("原价")
    private BigDecimal currentPrice = BigDecimal.valueOf(99d);

    @ApiModelProperty("拼团价")
    private BigDecimal collagePrice = BigDecimal.valueOf(59d);

    @ApiModelProperty("ColorVo 颜色列表")
    private List<ColorVo> colorList;

    @ApiModelProperty("商品详情图列表")
    private List<String> skuUrlList;
}
