package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupDraftVo;
import com.ihomefnt.o2o.intf.domain.program.dto.ReplaceAbleDto;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomImageDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.CabinetBomDto;
import com.ihomefnt.o2o.intf.manager.constant.common.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-03-21 14:59
 */
@Data
public class DraftInfoResponse {

    private Integer draftType;
    private Integer orderNum;
    private double draftProgress;
    private long draftProfileNum;
    private double operationProgress;
    @ApiModelProperty("状态 // 1已签约；2未签约；3历史签约 4.最新草稿")
    private Integer draftSignStatus = Constants.DRAFT_SIGN_STATUS_NO_SIGN;
    private String draftName;
    private String draftId;
    private DraftJsonStrBean draftContent;

    @Data
    public static class DraftJsonStrBean {

        private double process;
        @ApiModelProperty("草稿方案总价")
        private Integer totalPrice = 0;
        @ApiModelProperty("方案总价是否变化 0未变 1价格变化")
        private Integer totalPriceChangeFlag = 0;
        @ApiModelProperty("价格变动金额，正数增加，负数减少")
        private Integer totalPriceChangeAmount;
        private boolean hasAjustment;
        private Integer customSelectItemCount;
        private Integer houseTypeId;
        private Integer spaceReplaceCount; //空间替换个数
        private Integer hardReplaceCount; //硬装替换个数
        private Integer softReplaceCount; //软装替换个数
        private Integer specialExceptionStatus = Constants.SPECIAL_EXCEPTION_STATUS_NORMAL;//1异常 2正常 特殊异常场景，sku不在原方案中
        private Integer totalSelectionItemCount;
        @ApiModelProperty("bom差价总和")
        private BigDecimal bomTotalDiffPrice;
        private SolutionSelectedBean solutionSelected;
        private List<SpaceDesignSelectedBean> spaceDesignSelected;
        private Integer masterTaskStatus = 0;//0 没有渲染任务，1:未开始，2:进行中，3：已完成，4：失败
        @ApiModelProperty("渲染任务预计完成时间")
        private String preMasterTaskFinishTime;

        @ApiModelProperty("已替换“免费”商品空间数据")
        private ReplaceAbleDto replacedAbleDto;

        @ApiModelProperty("已替换“免费”商品空间数据")
        private ReplaceAbleDto replacedBomAbleDto;

        public synchronized void addTotalPrice(Integer totalPrice) {
            this.totalPrice = this.totalPrice + totalPrice;
        }

        public synchronized void delTotalPrice(Integer totalPrice) {
            this.totalPrice = this.totalPrice - totalPrice;
        }

        public synchronized void addBomTotalDiffPrice(BigDecimal bomTotalDiffPrice) {
            this.bomTotalDiffPrice = this.bomTotalDiffPrice.add(bomTotalDiffPrice);
        }


        @Data
        public static class SolutionSelectedBean {

            private Integer solutionId;
            @ApiModelProperty("全屋方案原价")
            private Integer solutionPrice;
            private Integer solutionType;
            private Integer compareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常
            private Integer message;//提示信息
            private String solutionAdvantage;
            private boolean customized;
            private Integer decorationType;
            private String solutionStyle;
            private String headImage;
            private String solutionName;
            @ApiModelProperty("平面设计图")
            private String solutionGraphicDesignUrl;
            @ApiModelProperty("拆改户型图")
            private String reformApartmentUrl;
            @ApiModelProperty("户型图")
            private String apartmentUrl;
            @ApiModelProperty("服务费明细")
            private List<ServiceItemDto> serviceItemList;
        }

        @Data
        @Accessors(chain = true)
        public static class SpaceDesignSelectedBean {

            private Integer roomId;
            private Integer spaceDesignId;//spaceDesignId和roomId相同，为了适配老代码增加该字段
            private SpaceBean defaultSpace;
            private SpaceBean selected;
            private String spaceUsageName;
            private Integer spaceUsageId;
            private Integer visibleFlag;
            @ApiModelProperty("是否有床垫不匹配 0:无，1:有")
            private Integer mattressMismatching = 0;
            private Integer insideSkuStatus = Constants.INSIDE_SKU_STATUS_NORMAL;//1 有下架商品 2正常 3 商品变价
            private List<SoftResponseListBean> softResponseList;
            private List<HardItemListBean> hardItemList;


            public SpaceDesignSelectedBean() {
            }

            public SpaceDesignSelectedBean(Integer spaceUsageId) {
                this.spaceUsageId = spaceUsageId;
            }

            @Data
            public static class SpaceBean {

                private Integer spaceDesignId;
                private String headImage;
                private String headImageOrigin;
                private Integer compareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常
                private Integer message;//提示信息
                private String spaceStyle;
                private Integer solutionId;
                private String solutionName;
                @ApiModelProperty("空间用途id")
                private String spaceUsageId;
                @ApiModelProperty("空间名称")
                private String spaceUsageName;
                @ApiModelProperty("空间图片")
                private RoomImageDto roomImage;
                private Integer spaceDesignPrice;
            }

            @Data
            @Accessors(chain = true)
            public static class SoftResponseListBean {

                private Integer roomId;
                private Integer defaultSkuId;
                private String category;
                private String superKey;
                @ApiModelProperty("DNA空间商品件数")
                private Integer itemCount;
                private Integer status;//0无改动 1新增 2删除 3修改
                private Integer visibleFlag;
                private Integer lastCategoryId;
                private String lastCategoryName;
                @ApiModelProperty("一级类目id")
                private Integer rootCategoryId;
                @ApiModelProperty("一级类目名称")
                private String rootCategoryName;
                private FurnitureBean furnitureSelected;
                private FurnitureBean furnitureDefault;
                @ApiModelProperty("空间组合默认")
                private BomGroupDraftVo bomGroupDefault;
                @ApiModelProperty("空间组合选中")
                private BomGroupDraftVo bomGroupSelected;
                private Integer itemType = 0;//0其他，1:床,2:床垫
                @ApiModelProperty("定制柜数据")
                private CabinetBomDto cabinetBomGroup;
                @ApiModelProperty("是否支持可视化云渲染")
                private boolean supportDrawCategory = false;
                @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
                private Integer freeAble = 0;


                @Data
                @Accessors(chain = true)
                public static class FurnitureBean implements Cloneable{

                    private Integer categoryLevelTwoId;
                    private Integer categoryLevelThreeId;
                    private boolean appCustomizable;
                    private String categoryLevelFourName;
                    private String smallImage;
                    private String color;
                    private String categoryLevelTwoName;
                    private String furnitureName;
                    private String itemName;
                    private boolean hasMore;
                    private Integer compareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常
                    private Integer skuCompareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常
                    private Integer message;//提示信息
                    private String styleName;
                    private Integer furnitureType;
                    private Integer styleId;
                    private BigDecimal price;
                    private BigDecimal priceDiff;
                    private String moreImageUrl;
                    private String itemSize;
                    private Integer parentSkuId;
                    private String brand;
                    private Integer skuId;
                    private Integer productType;
                    private String categoryLevelThreeName;
                    private Integer itemCount;
                    private String imgUrl;
                    private String material;
                    private Integer categoryLevelFourId;
                    private Integer visibleFlag;
                    private Integer lastCategoryId;
                    private String lastCategoryName;
                    @ApiModelProperty("高")
                    private Integer height;
                    @ApiModelProperty("长")
                    private Integer length;
                    @ApiModelProperty("宽")
                    private Integer width;
                    @ApiModelProperty("建议适配床垫最小高")
                    private Integer suggestMattressMinHeight;
                    @ApiModelProperty("建议适配床垫长")
                    private Integer suggestMattressLength;
                    @ApiModelProperty("建议适配床垫宽度")
                    private Integer suggestMattressWidth;
                    @ApiModelProperty("建议适配床垫最大高")
                    private Integer suggestMattressMaxHeight;

                    @Override
                    public FurnitureBean clone() {
                        FurnitureBean o = null;
                        try {
                            o = (FurnitureBean) super.clone();
                        } catch (CloneNotSupportedException e) {
                        }
                        return o;
                    }
                    @ApiModelProperty("1 是免费赠品 0 非免费赠品")
                    private Integer freeFlag = 0;

                    @ApiModelProperty("赠品展示标记 0不展示 1可替换为赠品 2免费赠品 4效果图推荐")
                    private Integer showFreeFlag = 0;

                    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
                    private Integer freeAble = 0;
                }

            }

            @Data
            public static class HardItemListBean {

                private boolean contain;
                private String hardItemName;
                private String hardItemDesc;
                private Integer hardItemId;
                private Integer status;//0无改动 1新增 2删除 3修改
                private String superKey;
                private Object hardItemImage;
                private HardItemBean hardItemSelected;
                private HardItemBean hardItemDefault;
                @ApiModelProperty("末级类目id")
                private Integer lastCategoryId;
                @ApiModelProperty("末级类目名称")
                private String lastCategoryName;
                @ApiModelProperty("组合已选项")
                private HardBomGroup hardBomGroupSelect;
                @ApiModelProperty("是否有替换项")
                private Boolean hasReplaceItem;
                @ApiModelProperty("组合默认项")
                private HardBomGroup hardBomGroupDefault;
                @ApiModelProperty("是否是标配 0否 1是")
                private Integer isStandardItem;
                @ApiModelProperty("定制柜数据")
                private CabinetBomDto cabinetBomGroup;

                @ApiModelProperty("是否支持可视化云渲染")
                private boolean supportDrawCategory = false;

                @Data
                @Accessors(chain = true)
                public static class HardItemBean implements Cloneable{

                    private ProcessSelectedBean processSelected;
                    private String smallImage;
                    private Integer compareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常
                    private Integer skuCompareStatus = Constants.COMPARE_STATUS_NORMAL;//1下架 2变价 3正常
                    private Integer message;//提示信息
                    private boolean defaultSelection;
                    private String headImage;
                    private Integer hardSelectionId;
                    private String hardSelectionDesc;
                    private String hardSelectionName;
                    @ApiModelProperty("末级类目id")
                    private Integer lastCategoryId;
                    @ApiModelProperty("末级类目名称")
                    private String lastCategoryName;

                    @Override
                    public HardItemBean clone() {
                        HardItemBean o = null;
                        try {
                            o = (HardItemBean) super.clone();
                        } catch (CloneNotSupportedException e) {
                        }
                        return o;
                    }

                    @Data
                    public static class ProcessSelectedBean {

                        private String smallImage;
                        private Integer processId;
                        private String processName;
                        private BigDecimal price;
                        private BigDecimal priceDiff;
                        private boolean processDefault;
                        private String processImage;
                        @ApiModelProperty("全屋子项")
                        private HardItemBean selectChildHardItem;
                        @ApiModelProperty("全屋子项集合")
                        private List<HardItemBean> hardItemSelection;
                    }
                }

            }
        }
    }
}
