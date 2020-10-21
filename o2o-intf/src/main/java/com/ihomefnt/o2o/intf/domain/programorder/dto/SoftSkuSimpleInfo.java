package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("软装sku简单信息")
public class SoftSkuSimpleInfo {

    @ApiModelProperty("skuId")
    private Integer skuId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品图片")
    private String productImage;

    @ApiModelProperty("商品个数")
    private Integer productCount;

    @ApiModelProperty("赠品标志 非0时，为赠品")
    private Integer giftFlag;

    @ApiModelProperty("商品状态")
    private Integer productStatus;

    @ApiModelProperty("商品状态")
    private String productStatusStr;

    @ApiModelProperty("（新）商品状态")
    private Integer newStatus;

    @ApiModelProperty("（新）商品状态名称")
    private String newStatusName;

    @ApiModelProperty("家具类型 2-赠品 4-bom组合")
    private Integer furnitureType;

    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("类目id")
    private Integer productCategory;

    @ApiModelProperty("类目名称")
    private String categoryName;

    @ApiModelProperty("末级类目id")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;
}
