package com.ihomefnt.o2o.intf.domain.dna.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-11-25 14:59
 */
@Data
@ApiModel("装修报价dna信息")
@Accessors(chain = true)
public class DnaPriceInfoDto {

    @ApiModelProperty("风格名称")
    private String dnaStyleName;

    @ApiModelProperty("DNA详细信息")
    private List<ListBean> list;

    @Data
    public static class ListBean {

        @ApiModelProperty("dnaId")
        private Integer dnaId;

        @ApiModelProperty("系列名称")
        private String dnaSeriesName;

        @ApiModelProperty("dna名称")
        private String dnaName;

        @ApiModelProperty("头图")
        private String headImgUrl;

        @ApiModelProperty("dna单位面积最大价格 单位元")
        private BigDecimal dnaMaxPrice;

        @ApiModelProperty("dna单位面积最小价格 单位元")
        private BigDecimal dnaMinPrice;

        @ApiModelProperty("dna单位面积平均价格 单位元")
        private BigDecimal dnaAvgPrice;
    }
}
