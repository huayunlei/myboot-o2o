package com.ihomefnt.o2o.intf.domain.art.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-07 10:34
 */

@ApiModel("新版艾商城产品数据")
@Data
public class ProductResponse {

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品id")
    private String productId;

    @ApiModelProperty("产品最低价")
    private BigDecimal minPrice;

    @ApiModelProperty("产品最高价")
    private BigDecimal maxPrice;

    @ApiModelProperty("产品图片")
    private List<String> productPicUrl;

    @ApiModelProperty("是否可用艾积分")
    private boolean exAble = true;

    @ApiModelProperty("产品详情")
    private String productDetail;

    @ApiModelProperty("是否支持特色定制 0 非 1是")
    private Integer specialFlag = 1;

    @ApiModelProperty("可定制内容")
    private String specialDesc;

    private Integer visitNum;//浏览量

    @ApiModelProperty("上下架标记 0上架 1下架")
    private Integer onlineStatus;

    @ApiModelProperty(value = "关联商品数量")
    private Integer skuCount = 0;

}
