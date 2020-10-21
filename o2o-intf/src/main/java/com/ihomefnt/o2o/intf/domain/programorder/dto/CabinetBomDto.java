package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liyonggang
 * @create 2019-07-24 09:48
 */
@Data
public class CabinetBomDto {

    @ApiModelProperty("家具类型：2 赠品家具, 4 bom 组合")
    private Integer furnitureType;

    @ApiModelProperty("组合头图")
    private String groupImage;

    @ApiModelProperty("是否有替换项")
    private Boolean hasReplaceItem = Boolean.FALSE;

    @ApiModelProperty("是否标配 0否 1是")
    private Integer isStandardItem;

    @ApiModelProperty("售价")
    private BigDecimal price;

    @ApiModelProperty("差价")
    private BigDecimal priceDiff;

    @ApiModelProperty("唯一标示")
    private String superKey;

    @ApiModelProperty("组合状态 //1下架 2变价 3正常")
    private Integer compareStatus = 3;//1下架 2变价 3正常

    @ApiModelProperty("变价金额")
    private BigDecimal changePrice = new BigDecimal(0);
    @ApiModelProperty("message")
    private BigDecimal message = new BigDecimal(0);

    @ApiModelProperty("类目id")
    private Integer secondCategoryId;

    @ApiModelProperty("类目名称")
    private String secondCategoryName;

    @ApiModelProperty("组合类型 7 定制窗帘 8 定制吊顶 9 硬装定制柜 10 软装定制柜")
    private Integer groupType;

    @ApiModelProperty("商品标记类型集合：2 赠品家具")
    private List<Integer> tagList;

    List<ReplaceBomDto>replaceBomList;

    @ApiModelProperty("颜色")
    private String colour;
    @ApiModelProperty("材质")
    private String texture;
}
