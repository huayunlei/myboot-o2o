package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 硬装组合信息
 *
 * @author liyonggang
 * @create 2019-05-15 10:54
 */
@Data
@ApiModel("硬装组合信息")
@Accessors(chain = true)
public class HardBomGroup implements Cloneable {


    @ApiModelProperty("艾佳售价，和price一样")
    private BigDecimal aijiaPrice;

    @ApiModelProperty("类目id")
    private Integer categoryId;

    @ApiModelProperty("类目名称")
    private String categoryName;

    @ApiModelProperty("家具类型：2 赠品家具, 4 bom 组合")
    private Integer furnitureType;

    @ApiModelProperty("组合分类描述 eg.窗帘布+窗帘纱+罗马杆")
    private String groupDesc;

    @ApiModelProperty("组合id")
    private Integer groupId;

    @ApiModelProperty("组合头图")
    private String groupImage;

    @ApiModelProperty("组合名称")
    private String groupName;

    @ApiModelProperty("是否有替换项")
    private Boolean hasReplaceItem = Boolean.FALSE;

    @ApiModelProperty("是否标配 0否 1是")
    private Integer isStandardItem;

    @ApiModelProperty("商品件数")
    private Integer itemCount;

    @ApiModelProperty("1 有选配物料 0 没有可选配物料")
    private Integer optionFlag;

    @ApiModelProperty("售价")
    private BigDecimal price;

    @ApiModelProperty("差价")
    private BigDecimal priceDiff;

    @ApiModelProperty("商品件数，和itemCount一样")
    private BigDecimal productAmount;

    @ApiModelProperty("组合名称，和groupName一样")
    private String productName;

    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    @ApiModelProperty("唯一标示")
    private String superKey;

    @ApiModelProperty("模板id")
    private Integer templateId;

    @ApiModelProperty("组合状态 //1下架 2变价 3正常")
    private Integer compareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常

    @ApiModelProperty("组合状态 //1下架 2变价 3正常")
    private Integer skuCompareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常

    @ApiModelProperty("message")
    private String message;

    @ApiModelProperty("变价金额，逻辑使用")
    private BigDecimal changePrice = new BigDecimal(0);

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

    @ApiModelProperty("组件集合")
    private List<ComponentList> componentList;


    @ApiModelProperty("商品标记类型集合：2 赠品家具")
    private List<Integer> tagList;

    @Override
    public HardBomGroup clone() {

        HardBomGroup o = null;
        try {
            o = (HardBomGroup) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return o;
    }

    @Data
    public static class ComponentList {
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

        @ApiModelProperty("组件售价")
        private BigDecimal componentPrice;

        @ApiModelProperty("组件采购价")
        private BigDecimal componentPurchasePrice;

        @ApiModelProperty("高 单位：mm")
        private Integer height;

        @ApiModelProperty("长 单位：mm")
        private Integer length;

        @ApiModelProperty("物料信息")
        private MaterialDetail materialDetail;

        @ApiModelProperty("1 有选配物料 0 没有可选配物料")
        private Integer optionFlag;

        @ApiModelProperty("父组件id")
        private Integer parentComponentId;

        @ApiModelProperty("物料用量")
        private Integer quantities;

        @ApiModelProperty("宽 单位：mm")
        private Integer width;

        @Data
        public static class MaterialDetail {
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

            @ApiModelProperty("物料id")
            private Integer materialId;

            @ApiModelProperty("物料名称")
            private String materialName;

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
            private BigDecimal purchasePrice;

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

            @Data
            public static class BomPackageList {
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
