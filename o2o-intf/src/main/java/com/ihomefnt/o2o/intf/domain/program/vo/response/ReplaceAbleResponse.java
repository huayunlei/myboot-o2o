package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupDraftVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-09-16 15:25
 */
@Data
public class ReplaceAbleResponse {

        @ApiModelProperty("软硬装内容明细")
        private List<ReplaceAbleResponse.SpaceDesignSelectedBean> spaceDesignSelected;

        @ApiModelProperty("替换后总差价")
        private BigDecimal totalPriceDiff;

        @Data
        @Accessors(chain = true)
        public static class SpaceDesignSelectedBean {

            private Integer spaceDesignId;
            private List<ReplaceAbleResponse.SpaceDesignSelectedBean.SoftResponseListBean> softResponseList;


            @Data
            @Accessors(chain = true)
            public static class SoftResponseListBean {

                private Integer roomId;
                private Integer defaultSkuId;
                private String category;
                private String superKey;
                @ApiModelProperty("DNA空间商品件数")
                private Integer itemCount;
                private Integer visibleFlag;
                private Integer lastCategoryId;
                private String lastCategoryName;
                @ApiModelProperty("一级类目id")
                private Integer rootCategoryId;
                @ApiModelProperty("一级类目名称")
                private String rootCategoryName;
                private ReplaceAbleResponse.SpaceDesignSelectedBean.SoftResponseListBean.FurnitureBean furnitureSelected;
                @ApiModelProperty("空间组合选中")
                private BomGroupDraftVo bomGroupSelected;
                @Data
                @Accessors(chain = true)
                public static class FurnitureBean {

                    private Integer categoryLevelTwoId;
                    private Integer categoryLevelThreeId;
                    private String categoryLevelFourName;
                    private String smallImage;
                    private String color;
                    private String categoryLevelTwoName;
                    private String furnitureName;
                    private String itemName;
                    private boolean hasMore;
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
                    private Integer freeFlag;
                    private Integer showFreeFlag;
                }

            }
        }
}
