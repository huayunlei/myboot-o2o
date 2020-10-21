package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-07 15:27
 */

@ApiModel("可定制商品信息对象")
@Data
public class CustomProductDto {

    @ApiModelProperty("商品图片")
    private String picUrl;

    @ApiModelProperty("产品id")
    private String productId;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品图片")
    private List<ArtWorkProductPicDto> productPicUrlList;

    @ApiModelProperty("商品id")
    private String skuId;

    @ApiModelProperty("作品编号")
    private String worksId;

    @ApiModelProperty("作品名称")
    private String worksName;

    @ApiModelProperty("作品图片")
    private String worksPicUrl;

    @ApiModelProperty("产品首图")
    private String productPicUrl;

    @ApiModelProperty("是否支持特色定制 0 非 1是")
    private Integer specialFlag = 0;

}
