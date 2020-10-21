package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.google.common.collect.Sets;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupDraftVo;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomImageDto;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HardBomGroup;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ServiceItemDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.CabinetBomDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author wanyunxin
 * @create 2019-03-20 21:22
 */
@Data
@ApiModel("草稿请求参数")
public class DraftInfoRequest extends HttpBaseRequest {

    private Integer orderId;
    private String draftName;
    private double draftProgress;
    private Long draftProfileNum;
    @ApiModelProperty("状态 // 1已签约；2未签约；3历史签约 4.最新草稿 更新时必传")
    private Integer draftSignStatus;
    private Integer draftType;
    private DraftContentBean draftContent;
    private Integer userId;
    @ApiModelProperty("是否进行了换免费赠品的操作 0 否 1是")
    private Integer onceReplaceFlag = 0;

    @Data
    public static class DraftContentBean {

        private double process;
        @ApiModelProperty("草稿方案总价")
        private Integer totalPrice;
        private boolean hasAjustment;
        private Integer customSelectItemCount;
        private Integer houseTypeId;
        private Integer spaceReplaceCount;
        private Integer totalSelectionItemCount;
        private SolutionSelectedBean solutionSelected;
        private List<SpaceDesignSelectedBean> spaceDesignSelected;
        @ApiModelProperty("需要渲染的空间id集合")
        private Set<Integer> needDrawRoomList = Sets.newHashSet();
        @ApiModelProperty("草稿离线渲染主任务状态 0:没有渲染任务 1:未开始，2:进行中，3：已完成，4：失败")
        private Integer masterTaskStatus;
        @ApiModelProperty("渲染任务预计完成时间")
        private String preMasterTaskFinishTime;
        @ApiModelProperty("图片仅供参考的空间集合")
        private Set<Integer> referenceOnlyRoomList;

        @Data
        public static class SolutionSelectedBean {

            private Integer solutionId;
            private String solutionStyle;
            @ApiModelProperty("方案类型名称")
            private String solutionName;//新增
            private String headImage;
            @ApiModelProperty("全屋方案原价")
            private Integer solutionPrice;
            @ApiModelProperty("方案类型 0软装+硬装 1纯软装")
            private Integer decorationType;

            @ApiModelProperty("户型版本号")
            private Long apartmentVersion;

            @ApiModelProperty("是否是拆改方案 0 不是 1 是")
            private Integer reformFlag;

            @ApiModelProperty("户型id")
            private Long apartmentId;
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

            @ApiModelProperty("空间用途名称")
            private String spaceUsageName;
            private Integer spaceUsageId;
            private Integer roomId;
            private String headImage;
            private String headImageOrigin;
            private SelectedBean defaultSpace;
            private SelectedBean selected;
            private List<SoftResponseListBean> softResponseList;
            private List<HardItemListBean> hardItemList;

            @Data
            @Accessors(chain = true)
            public static class SelectedBean implements Cloneable {

                private Integer spaceDesignId;
                private Integer solutionId;
                private Integer spaceDesignPrice;
                private String headImage;
                private String headImageOrigin;
                private String spaceUsageName;//新增
                private String spaceUsageId;//新增
                private String solutionName;//新增
                private String spaceStyle;//新增
                @ApiModelProperty("空间图片")
                private RoomImageDto roomImage;

                @Override
                public SelectedBean clone() {
                    SelectedBean o = null;
                    try {
                        o = (SelectedBean) super.clone();
                    } catch (CloneNotSupportedException e) {
                    }
                    return o;
                }
            }

            @Data
            @Accessors(chain = true)
            public static class SoftResponseListBean {

                private Integer defaultSkuId;
                private Integer status = 0;
                private String superKey;//唯一标识
                private Integer visibleFlag;//是否支持可视化 1支持 0不支持
                private String category;//类目
                private String lastCategoryName;
                private Integer lastCategoryId;
                private FurnitureSelectedBean furnitureSelected;
                private FurnitureSelectedBean furnitureDefault;
                @ApiModelProperty("空间组合默认")
                private BomGroupDraftVo bomGroupDefault;
                @ApiModelProperty("空间组合选中")
                private BomGroupDraftVo bomGroupSelected;
                @ApiModelProperty("定制柜数据")
                private CabinetBomDto cabinetBomGroup;

                @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
                private Integer freeAble = 0;
                @Data
                public static class FurnitureSelectedBean implements Cloneable {
                    /**
                     * categoryLevelTwoId : 4
                     * price : 3590
                     * skuId : 146187
                     */

                    private Integer categoryLevelTwoId;
                    private String lastCategoryName;
                    private Integer lastCategoryId;
                    @ApiModelProperty("是否可定制")
                    private boolean appCustomizable;
                    private BigDecimal price;
                    private BigDecimal priceDiff;
                    @ApiModelProperty("DNA空间商品件数")
                    private Integer itemCount;
                    private String itemName;
                    private Integer visibleFlag;//是否支持可视化 1支持 0不支持
                    private String smallImage;
                    private Integer skuId;
                    private String material;
                    private String brand;
                    private String color;
                    private Integer furnitureType;
                    private String furnitureName;
                    private Integer productType;

                    @ApiModelProperty("1 是免费赠品 0 非免费赠品")
                    private Integer freeFlag = 0;

                    @ApiModelProperty("赠品展示标记 0不展示 1可替换为赠品 2免费赠品 4效果图推荐")
                    private Integer showFreeFlag = 0;

                    @Override
                    public FurnitureSelectedBean clone() {

                        FurnitureSelectedBean o = null;
                        try {
                            o = (FurnitureSelectedBean) super.clone();
                        } catch (CloneNotSupportedException e) {
                        }
                        return o;
                    }
                }
            }

            @Data
            public static class HardItemListBean {

                private Integer hardItemId;//类目id
                private String hardItemName;
                private Integer status = 0;//0无改动 1新增 2删除 3修改
                private String superKey;//唯一标识
                private HardItemBean hardItemSelected;
                private HardItemBean hardItemDefault;
                private HardBomGroup hardBomGroupSelect;
                private HardBomGroup hardBomGroupDefault;
                @ApiModelProperty("定制柜数据")
                private CabinetBomDto cabinetBomGroup;

                @Data
                @Accessors(chain = true)
                public static class HardItemBean implements Cloneable{

                    private Integer hardSelectionId;
                    private String hardSelectionName;
                    private ProcessBean processSelected;
                    private String smallImage;

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
                    @Accessors(chain = true)
                    public static class ProcessBean {

                        private Integer processId;
                        private BigDecimal price;
                        private BigDecimal priceDiff;
                        private String processName;
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
