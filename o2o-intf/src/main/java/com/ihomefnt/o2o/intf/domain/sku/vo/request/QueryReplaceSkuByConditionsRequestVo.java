package com.ihomefnt.o2o.intf.domain.sku.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("搜索同类SKU入参")
@Data
public class QueryReplaceSkuByConditionsRequestVo extends HttpBaseRequest {

    @ApiModelProperty("当前页数 默认0")
    private Integer pageNo = 1;

    @ApiModelProperty("每页的记录数据 默认10")
    private Integer pageSize = 10;

    @ApiModelProperty("搜索条件")
    private SearchCondition searchCondition;
    @ApiModelProperty("查询家具类型,0:通用家具,1:床,2:床垫")
    private Integer itemType = 0;
    @ApiModelProperty("建议适配床垫最小高")
    private Integer suggestMattressMinHeight;
    @ApiModelProperty("建议适配床垫长")
    private Integer suggestMattressLength;
    @ApiModelProperty("建议适配床垫宽度")
    private Integer suggestMattressWidth;
    @ApiModelProperty("建议适配床垫最大高")
    private Integer suggestMattressMaxHeight;
    @ApiModelProperty("规格查询")
    private String itemSize;

    @ApiModel("app sku 搜索条件")
    @Data
    public static class SearchCondition {

        @ApiModelProperty("是否限制必须有模型 0：否 ,1：是")
        private Integer modelFlag;

        @ApiModelProperty("排序规则 0默认 1差价升序 2差价降序")
        private Integer sort = 0;

        @ApiModelProperty("风格")
        private Integer styleId;

        @ApiModelProperty("标配SKUID")
        private Integer baseSkuId;

        @ApiModelProperty("标配skuId数量")
        private Integer baseSkuCount;

        @ApiModelProperty("家具类型")
        private Integer furnitureType;

        @ApiModelProperty("需要排除的skuId")
        private List<Integer> excludeSkuIdList;

        @ApiModelProperty("类目id集合")
        private List<Integer> categoryIdList;

        @ApiModelProperty("品牌id集合")
        private List<Integer> brandIdList;

        @ApiModelProperty("属性值id集合")
        private List<Integer> propertyValueIdList;

        @ApiModelProperty("sku长度范围")
        private SizeRange lengthRange;

        @ApiModelProperty("sku高度范围")
        private SizeRange heightRange;

        @ApiModelProperty("sku宽度范围")
        private SizeRange widthRange;

        @ApiModelProperty("价格 指的是原始的价格 *count")
        private BigDecimal baseSkuTotalPrice;

        @ApiModelProperty("已选skuID")
        private Integer selectedSkuId;

        @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
        private Integer freeAble = 0;

        @Data
        @ApiModel("尺寸范围")
        @Accessors(chain = true)
        public static class SizeRange {

            @ApiModelProperty("最小值")
            private Integer minValue;

            @ApiModelProperty("最大值")
            private Integer maxValue;

        }


    }


}
