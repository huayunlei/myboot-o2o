package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-07 14:46
 */
@ApiModel("艺术品商品对象")
@Data
public class ArtSkuDto {

    @ApiModelProperty("商品id")
    private String skuId;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("商品图片")
    private String picUrl;

    @ApiModelProperty("商品图片列表")
    private List<String> picUrlList;

    @ApiModelProperty("属性")
    private List<PropertyDto> properties;

    @ApiModelProperty("产品编号")
    private String productId;

    @ApiModelProperty("作品编号")
    private String worksId;

    @ApiModelProperty("上下架标记 0上架 1下架")
    private Integer onlineStatus;

    @ApiModelProperty("是否支持特色定制 0 非 1是")
    private Integer specialFlag = 0;
}
