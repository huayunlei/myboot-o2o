package com.ihomefnt.o2o.intf.domain.homepage.dto;

import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-03-26 20:23
 */
@Data
@ApiModel("BOM草稿中结构")
@Accessors(chain = true)
public class BomGroupDraftVo implements Cloneable{

    @ApiModelProperty("类目id")
    private Integer categoryId;
    @ApiModelProperty("类目名称")
    private String categoryName;
    private Integer lastCategoryId;
    private String lastCategoryName;
    @ApiModelProperty("家具类型：2 赠品家具, 4 bom 组合")
    private Integer furnitureType;
    @ApiModelProperty("组合分类描述 eg.窗帘布+窗帘纱+罗马杆")
    private String groupDesc;
    @ApiModelProperty("组合id")
    private Integer groupId;
    @ApiModelProperty("组合图片")
    private String groupImage;
    private Integer compareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常
    private Integer skuCompareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常
    private String message ;
    @ApiModelProperty("组合名称")
    private String groupName;
    @ApiModelProperty("商品数量")
    private Integer itemCount;
    @ApiModelProperty("售价")
    private BigDecimal price;
    @ApiModelProperty("差价")
    private BigDecimal priceDiff = new BigDecimal(0);
    @ApiModelProperty("商品标记类型集合：2 赠品家具")
    private List<Integer> tagList;

    @ApiModelProperty(value = "柜体标签编号")
    private String cabinetType;

    @ApiModelProperty(value = "柜体标签: 衣柜1 衣柜2 吊柜 地柜")
    private String cabinetTypeName;

    @ApiModelProperty("类目id")
    private Integer secondCategoryId;

    @ApiModelProperty("类目名称")
    private Integer secondCategoryName;

    @ApiModelProperty("组合类型 7 定制窗帘 8 定制吊顶 9 硬装定制柜 10 软装定制柜")
    private Integer groupType;

    @ApiModelProperty("位置索引")
    private String positionIndex;

    @ApiModelProperty("1 是免费赠品 0 非免费赠品")
    private Integer freeFlag = 0;

    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
    private Integer freeAble = 0;

    private Integer showFreeFlag;


    @Override
    public BomGroupDraftVo clone() {

        BomGroupDraftVo o = null;
        try {
            o = (BomGroupDraftVo) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return o;
    }
}
