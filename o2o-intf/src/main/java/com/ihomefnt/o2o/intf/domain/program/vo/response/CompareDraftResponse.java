package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-06-25 10:13
 */
@Data
@ApiModel("草稿比对返回信息")
public class CompareDraftResponse implements Serializable {

    @ApiModelProperty("草稿简要信息")
    private List<DraftSimpleInfoListBean> draftSimpleInfoList;

    @ApiModelProperty("方案基础信息")
    private List<BaseInfoListBean> baseInfoList;

    @ApiModelProperty("各空间信息")
    private List<SpaceInfoListBean> spaceInfoList;

    @Data
    @Accessors(chain = true)
    public static class DraftSimpleInfoListBean {

        @ApiModelProperty("草稿id")
        private String draftId;

        @ApiModelProperty("草稿名称")
        private String draftName;

        @ApiModelProperty("草稿状态 // 1已签约；2未签约；3历史签约 4.最新草稿")
        private Integer draftSignStatus = 2;

        @ApiModelProperty("已选方案总价")
        private BigDecimal totalPrice;

        @ApiModelProperty("平面设计图地址")
        private String designUrl;
    }

    @Data
    @Accessors(chain = true)
    public static class BaseInfoListBean {

        @ApiModelProperty("外部名称")
        private String columnName;

        @ApiModelProperty("是否整行高亮显示 0 不高亮 1高亮")
        private Integer rowHighLight = 0;

        @ApiModelProperty("行数据信息")
        private List<RowListBean> rowList;

        @Data
        public static class RowListBean {

            @ApiModelProperty("单列中多行信息")
            private List<RowInnerListBean> rowInnerList;

            @ApiModelProperty("是否显示平面设计图，0不显示 1显示")
            private Integer designUrlFlag = 0;

            @Data
            public static class RowInnerListBean {

                @ApiModelProperty("具体显示内容")
                private String rowInnerName;

                @ApiModelProperty("替换项总计")
                private Integer replaceCount;

                @ApiModelProperty("替换项差价总计")
                private Integer replaceAmountTotal;

                public RowInnerListBean() {
                }

                public RowInnerListBean(String rowInnerName, Integer replaceCount, Integer replaceAmountTotal) {
                    this.rowInnerName = rowInnerName;
                    this.replaceCount = replaceCount;
                    this.replaceAmountTotal = replaceAmountTotal;
                }

                public RowInnerListBean(String rowInnerName) {
                    this.rowInnerName = rowInnerName;
                }
            }
        }
    }

    @Data
    @Accessors(chain = true)
    public static class SpaceInfoListBean {

        @ApiModelProperty("空间用途id")
        private Integer spaceUsageId;

        @ApiModelProperty("空间用途名称")
        private String spaceUsageName;

        @ApiModelProperty("已选空间信息")
        private List<CompareDraftResponse.SpaceInfoListBean.SpaceSelectedBean> spaceSelected;

        @ApiModelProperty("各对比空间效果图信息")
        private List<CompareDraftResponse.SpaceInfoListBean.SpaceImageListBean> spaceImageList;

        @ApiModelProperty("各对比空间效果图信息")
        private List<CompareDraftResponse.SpaceInfoListBean.SpaceUsageListBean> spaceUsageList;

        @ApiModelProperty("空间硬装信息")
        private List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean> hardItemList;

        @ApiModelProperty("空间软装信息")
        private List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean> softResponseList;

        @Data
        @Accessors(chain = true)
        public static class SpaceSelectedBean {

            @ApiModelProperty("方案名称")
            private String solutionName;

            @ApiModelProperty("方案风格")
            private String spaceStyle;

            @ApiModelProperty("方案差价")
            private Integer spaceDesignPriceDiff;

            @ApiModelProperty("是否整行高亮显示 0 不高亮 1高亮")
            private Integer rowHighLight = 0;
        }

        @Data
        public static class SpaceImageListBean {

            @ApiModelProperty("空间图片")
            private String spaceImage;
        }
        @Data
        public static class SpaceUsageListBean {

            @ApiModelProperty("空间用途")
            private String spaceUsageName;

            @ApiModelProperty("是否整行高亮显示 0 不高亮 1高亮")
            private Integer rowHighLight = 0;
        }

        @Data
        public static class HardItemListBean {

            @ApiModelProperty("硬装选配项目名称")
            private String hardItemName;

            @ApiModelProperty("硬装选配项目id")
            private Integer hardItemId;

            @ApiModelProperty("是否整行高亮显示 0 不高亮 1高亮")
            private Integer rowHighLight = 0;

            @ApiModelProperty("硬装多组信息（处理同类目问题）")
            private List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean> hardItemClassGroupList;

            @Data
            public static class HardItemClassGroupListBean {

                @ApiModelProperty("硬装信息列表")
                private List<CompareDraftResponse.SpaceInfoListBean.HardItemListBean.HardItemClassGroupListBean.HardItemClassListBean> hardItemClassList;

                @Data
                @Accessors(chain = true)
                public static class HardItemClassListBean {

                    @ApiModelProperty("硬装名称信息")
                    private String hardSelectionName;

                    @ApiModelProperty("硬装ID")
                    private Integer hardSelectionId;

                    @ApiModelProperty("硬装替换差价")
                    private Integer priceDiff;

                    @ApiModelProperty("sku状态信息 //1下架 2变价 3正常")
                    private Integer skuCompareStatus = Constants.COMPARE_STATUS_NORMAL;

                    @ApiModelProperty("bom标识 1:bom组合 0:普通商品")
                    private Integer bomFlag = 0;

                    @ApiModelProperty("定制柜bom 集合")
                    private List<QueryCabinetPropertyListRequest.GroupQueryRequest> guiBomList;
                }
            }
        }

        @Data
        public static class SoftResponseListBean {

            @ApiModelProperty("软装类目名称")
            private String lastCategoryName;

            @ApiModelProperty("软装类目名称")
            private Integer lastCategoryId;

            @ApiModelProperty("家具类型：0成品，1定制品 2 赠品, 4 bom组合 ")//外层furnitureType用来排序
            private Integer furnitureType;

            @ApiModelProperty("是否整行高亮显示 0 不高亮 1高亮")
            private Integer rowHighLight = 0;

            @ApiModelProperty("软装多组信息（处理同类目问题）")
            private List<SoftResponseClassGroupListBean> softResponseClassGroupList;


            @Data
            @Accessors(chain = true)
            public static class SoftResponseClassGroupListBean {

                @ApiModelProperty("软装数据列表")
                private List<CompareDraftResponse.SpaceInfoListBean.SoftResponseListBean.SoftResponseClassGroupListBean.SoftResponseClassListBean> softResponseClassList;

                @Data
                @Accessors(chain = true)
                public static class SoftResponseClassListBean {

                    @ApiModelProperty("软装skuid")
                    private Integer skuId;

                    @ApiModelProperty("材质")
                    private String material;

                    @ApiModelProperty("软装商品名称（仅用于bom名称显示）")
                    private String furnitureName;

                    @ApiModelProperty("定制柜bom 集合")
                    private List<QueryCabinetPropertyListRequest.GroupQueryRequest> guiBomList;

                    @ApiModelProperty("商品品牌")
                    private String brand;

                    @ApiModelProperty("颜色")
                    private String color;

                    @ApiModelProperty("替换差价")
                    private Integer priceDiff;

                    @ApiModelProperty("家具类型：0成品，1定制品 2 赠品, 4 bom组合")
                    private Integer furnitureType;

                    @ApiModelProperty("sku状态信息 //1下架 2变价 3正常")
                    private Integer skuCompareStatus = Constants.COMPARE_STATUS_NORMAL;

                    @ApiModelProperty("bom标识 1:bom组合 0:普通商品 102 定制柜")
                    private Integer bomFlag = 0;
                }
            }
        }

    }
}
