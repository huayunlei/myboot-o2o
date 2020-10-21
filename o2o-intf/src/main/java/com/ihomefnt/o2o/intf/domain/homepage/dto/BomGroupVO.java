package com.ihomefnt.o2o.intf.domain.homepage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author liyonggang
 * @create 2019-03-20 21:29
 */
@Data
@ApiModel("方案中组合信息")
public class BomGroupVO implements Serializable {

    @ApiModelProperty("类目id")
    private Integer categoryId;
    @ApiModelProperty("类目名称")
    private String categoryName;
    @ApiModelProperty("家具类型：2 赠品家具, 4 bom 组合")
    private Integer furnitureType;
    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;
    @ApiModelProperty("末级类目ID")
    private Integer lastCategoryId;
    @ApiModelProperty("组合分类描述 eg.窗帘布+窗帘纱+罗马杆")
    private String groupDesc;
    @ApiModelProperty("组合id")
    private Integer groupId;
    @ApiModelProperty("组合图片")
    private String groupImage;
    @ApiModelProperty("组合名称")
    private String groupName;
    @ApiModelProperty("商品数量")
    private Integer itemCount;
    @ApiModelProperty("售价")
    private BigDecimal price;
    @ApiModelProperty("差价")
    private BigDecimal priceDiff;
    @ApiModelProperty("采购价")
    private Integer purchasePrice;
    @ApiModelProperty("组件集合")
    private List<ComponentList> componentList;
    @ApiModelProperty("商品标记类型集合：2 赠品家具")
    private List<Integer> tagList;

    @ApiModelProperty(value = "柜体标签编号")
    private String cabinetType;

    @ApiModelProperty(value = "柜体标签: 衣柜1 衣柜2 吊柜 地柜")
    private String cabinetTypeName;

    @ApiModelProperty("类目id")
    private Integer secondCategoryId;

    @ApiModelProperty("类目名称")
    private String secondCategoryName;

    @ApiModelProperty("组合类型 7 定制窗帘 8 定制吊顶 9 硬装定制柜 10 软装定制柜")
    private Integer groupType;

    @ApiModelProperty("位置索引")
    private String positionIndex;
    @ApiModelProperty("赠品展示标记 0不展示 1可替换为赠品 2免费赠品 4效果图推荐")
    private Integer showFreeFlag = 0;

    @ApiModelProperty("1 是免费赠品 0 非免费赠品")
    private Integer freeFlag = 0;

    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选 groupId层根据组件层的freeAble，有一个可选即为可选")
    private Integer freeAble = 0;


    /**
     * 组件信息
     */
    @Data
    @ApiModel("组件信息")
    public static class ComponentList implements Serializable {
        @ApiModelProperty("类目id")
        private Integer categoryId;
        @ApiModelProperty("类目名称")
        private String categoryName;
        @ApiModelProperty("组件id")
        private Integer componentId;
        @ApiModelProperty("组件名称")
        private String componentName;
        @ApiModelProperty("组件数量")
        private Integer componentNum;
        @ApiModelProperty("高 单位：mm")
        private Integer height;
        @ApiModelProperty("长（深）单位：mm")
        private Integer length;
        @ApiModelProperty("物料信息")
        private MaterialDetail materialDetail;
        @ApiModelProperty("父组件id")
        private Integer parentComponentId;
        @ApiModelProperty("物料用量")
        private Integer quantities;
        @ApiModelProperty("宽 单位：mm")
        private Integer width;
        @ApiModelProperty("组件分类名称。定制柜详情使用")
        private String componentCategoryName;

        @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
        private Integer freeAble = 0;

        /**
         * 组件信息
         */
        @Data
        @ApiModel("组件信息")
        public static class MaterialDetail implements Serializable {
            @ApiModelProperty("品牌id")
            private Integer brandId;
            @ApiModelProperty("品牌名称")
            private String brandName;
            @ApiModelProperty("计价单位：1=个 2=长度 3=周长 4=面积")
            private Integer chargeUnit;
            @ApiModelProperty("计价单位名称")
            private String chargeUnitName;
            @ApiModelProperty("物料头图")
            private String mainUrl;
            @ApiModelProperty("厂家型号")
            private String manufacturerModel;
            @ApiModelProperty(value = "物料图片，bom详情使用")
            private String materialImage;
            @ApiModelProperty("物料id")
            private Integer materialId;
            @ApiModelProperty("物料名称")
            private String materialName;

            @ApiModelProperty("1 是免费赠品 0 非免费赠品")
            private Integer freeFlag = 0;

            @ApiModelProperty("包裹数")
            private Integer packageSum;
            @ApiModelProperty("打包数")
            private Integer packingNum;
            @ApiModelProperty("打包对应关系数")
            private Integer packingRelationNum;
            @ApiModelProperty("父品牌id")
            private Integer parentBrandId;
            @ApiModelProperty("父品牌名称")
            private String parentBrandName;
            @ApiModelProperty("售价")
            private BigDecimal price;
            @ApiModelProperty("采购价")
            private Integer purchasePrice;
            @ApiModelProperty("系列id")
            private Integer seriesId;
            @ApiModelProperty("系列名称")
            private String seriesName;
            @ApiModelProperty("规格备注")
            private String specificationNote;
            @ApiModelProperty("规格")
            private String specifications;
            @ApiModelProperty("供应商id")
            private Integer supplierId;
            @ApiModelProperty("物料包裹集合")
            private List<BomPackageList> bomPackageList;

            /**
             * 包裹信息
             */
            @Data
            @ApiModel("包裹信息")
            public static class BomPackageList implements Serializable {
                @ApiModelProperty("包裹id")
                private Integer id;
                @ApiModelProperty("包裹名")
                private String name;
                @ApiModelProperty("包装体积")
                private String packingVolume;
                @ApiModelProperty("毛重")
                private String roughWeight;
            }
        }
    }
}
