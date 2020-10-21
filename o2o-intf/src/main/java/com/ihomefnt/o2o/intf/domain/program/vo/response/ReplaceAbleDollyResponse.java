package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupDraftVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-09-19 22:17
 */
@NoArgsConstructor
@Data
public class ReplaceAbleDollyResponse {

    @ApiModelProperty("一键替换总差价")
    private BigDecimal totalPriceDiff;
    private Long solutionId;
    private List<SpaceDesignListBean> spaceDesignList;

    @Data
    public static class SpaceDesignListBean {

        private Integer roomId;
        private String superKey;
        private BigDecimal priceDiff;
        private Integer lastCategoryId;
        private String lastCategoryName;
        private FurnitureBean furniture;
        private BomGroupDraftVo bomGroup;

        @Data
        public static class FurnitureBean {

            private Integer defaultSkuId;
            private BigDecimal actualPrice;
            private boolean appCustomizable;
            private Integer categoryLevelFourId;
            private String categoryLevelFourName;
            private Integer categoryLevelThreeId;
            private String categoryLevelThreeName;
            private Integer categoryLevelTwoId;
            private String categoryLevelTwoName;
            private Integer furnitureType;
            private String itemBrand;
            private String itemColor;
            private Integer itemCount;
            private String itemImage;
            private String itemMaterial;
            private String itemName;
            private String itemSize;
            private Integer lastCategoryId;
            private String lastCategoryName;
            private String material;
            private Integer parentSkuId;
            private BigDecimal price;
            private BigDecimal priceDiff;
            private Integer productType;
            private String ruleSize;
            private String seriesName;
            private Integer skuId;
            private BigDecimal skuPrice;
            private BigDecimal skuPurchasePrice;
            private Integer skuVisibleFlag;
            private Integer styleId;
            private String styleName;
            private Integer typeTwoId;
            private String typeTwoName;
        }
    }
}
