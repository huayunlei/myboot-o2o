package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by lindan on 2017/8/3.
 */
@ApiModel("硬装商品明细对象")
@Data
public class HardProductDetailDto {

    @ApiModelProperty("skuId")
    private Integer skuId;

    @ApiModelProperty("图片路径")
    private String imageUrl;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("选配包别名")
    private String shortName;

    @ApiModelProperty("硬装分类id")
    private Integer roomClassId;

    @ApiModelProperty("硬装分类名称")
    private String roomClassName;

    @ApiModelProperty("硬装分类描述")
    private String roomClassDescription;

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

    @ApiModelProperty("工艺id")
    private Integer craftId;

    @ApiModelProperty("工艺名称")
    private String craftName;

    @ApiModelProperty("硬装全屋选配包ID")
    private Integer groupId;

    @ApiModelProperty("硬装全屋选配包图片")
    private String groupImage;

    @ApiModelProperty("硬装全屋选配包名称")
    private String groupName;

    private Integer parentSkuId;
    private Integer parentCraftId;

    @ApiModelProperty("子商品")
    private List<HardProductDetailDto> childHardProductList;
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

    @ApiModelProperty("1:bom组合 0:普通商品")
    private Integer bomFlag = 0;

}
