package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by lindan on 2017/8/3.
 */
@Data
@ApiModel("软装商品明细对象")
public class SoftProductDetailDto {

    @ApiModelProperty("skuId")
    private Integer skuId;

    @ApiModelProperty("图片路径")
    private String imageUrl;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("品牌&系列名称")
    private String brandAndSeries;

    @ApiModelProperty("商品规格")
    private String specifications;

    @ApiModelProperty("商品数量")
    private Integer productCount;

    @ApiModelProperty("商品类型")
    private Integer furnitureType;

    @ApiModelProperty("空间id")
    private Long roomId;

    @ApiModelProperty("订单类型")
    private Integer orderType;

    @ApiModelProperty("属性")
    private Integer propertyType;

    @ApiModelProperty("二级类目")
    private String categoryName;

    @ApiModelProperty("材质")
    private String material;

    @ApiModelProperty("颜色")
    private String productColor;

    private Integer productStatus;//商品状态

    private String productStatusName;//商品状态:-1待交付 0待采购1采购中2代送货3送货中4送货完成7以取消

    private Integer bomFlag;//1:bom组合 0:普通商品

    private Integer lastCategoryId;// 末级类目Id

    private String lastCategoryName;// 末级类目名称

    @ApiModelProperty("二级类目id")
    private Integer groupSecondCategoryId;

    @ApiModelProperty("二级类目")
    private String groupSecondCategoryName;

    @ApiModelProperty("组合类型  4 窗帘 9 硬装定制柜 10 软装定制柜")
    private Integer groupType;

    @ApiModelProperty(value = "柜体标签编号")
    private String cabinetType;

    @ApiModelProperty(value = "柜体标签: 衣柜1 衣柜2 吊柜 地柜")
    private String cabinetTypeName;

    @ApiModelProperty(value = "位置索引")
    private String positionIndex;
}
