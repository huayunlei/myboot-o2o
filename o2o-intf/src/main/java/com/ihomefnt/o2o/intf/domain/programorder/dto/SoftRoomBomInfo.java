package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("软装BOM组合信息")
@Data
public class SoftRoomBomInfo {

    private Long id;

    @ApiModelProperty("空间id")
    private Long roomId;

    @ApiModelProperty("清单显示名称")
    private String categoryName;

    @ApiModelProperty("组合分类id")
    private Integer groupCategoryId;

    @ApiModelProperty("组合分类名称")
    private String groupCategoryName;

    @ApiModelProperty("组合id")
    private Long groupId;

    @ApiModelProperty("组合名称")
    private String groupName;

    @ApiModelProperty("组合数量")
    private Integer groupNum;

    @ApiModelProperty("组合图")
    private String groupImage;

    @ApiModelProperty("组合类型 11：软装 12：硬装")
    private Integer type;

    @ApiModelProperty("家具类型 取值：0成品家具，1定制家具，2赠品家具")
    private Integer furnitureType;

    @ApiModelProperty("赠品标志 非0时，为赠品")
    private Integer giftFlag;

    @ApiModelProperty("空间用途名称")
    private String roomName;

    @ApiModelProperty("商品状态")
    private Integer productStatus;

    @ApiModelProperty("商品状态")
    private String productStatusStr;

    @ApiModelProperty("（新）商品状态")
    private Integer newStatus;

    @ApiModelProperty("（新）商品状态名称")
    private String newStatusName;

    @ApiModelProperty(value = "柜体标签编号")
    private String cabinetType;

    @ApiModelProperty(value = "柜体标签: 衣柜1 衣柜2 吊柜 地柜")
    private String cabinetTypeName;

    @ApiModelProperty("二级类目id")
    private Integer groupSecondCategoryId;

    @ApiModelProperty("二级类目")
    private String groupSecondCategoryName;

    @ApiModelProperty("组合类型 7定制窗帘 8定制吊顶 9硬装定制柜 10软装定制柜")
    private Integer groupType;

    @ApiModelProperty("组合id集合")
    private List<GroupQueryRequest> queryList;

    @Data
    public static class GroupQueryRequest{

        @ApiModelProperty(value = "柜体标签编号")
        private String cabinetType;

        @ApiModelProperty(value = "柜体标签: 衣柜1 衣柜2 吊柜 地柜")
        private String cabinetTypeName;

        @ApiModelProperty(value = "组合id")
        private Long groupId;

        @ApiModelProperty(value = "默认组合id")
        private Long defaultGroupId;

        @ApiModelProperty(value = "默认组合数量")
        private Integer defaultGroupNum;

    }
}
