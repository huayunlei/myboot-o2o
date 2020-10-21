package com.ihomefnt.o2o.intf.domain.homepage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-03-22 22:43
 */
@Data
public class ComparePriceRequest {

    private Integer roomId;
    private List<HardReplaceListBean> hardReplaceList;
    private List<SoftReplaceListBean> softReplaceList;

    @Data
    @Accessors(chain = true)
    public static class HardReplaceListBean {

        private Integer craftId;
        private Integer newCraftId;
        private Integer newSkuId;
        @ApiModelProperty("差价")
        private BigDecimal priceDiff;
        private Integer skuId;
        @ApiModelProperty("父商品SkuId")
        private Integer parentSkuId;
        @ApiModelProperty("父商品工艺Id")
        private Integer parentCraftId;
    }

    @Data
    public static class SoftReplaceListBean {

        private Integer furnitureType;
        private Integer newSkuId;
        private BigDecimal priceDiff;
        private Integer skuId;
    }
}
