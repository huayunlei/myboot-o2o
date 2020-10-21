package com.ihomefnt.o2o.intf.domain.dna.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-11-18 10:30
 */
@Data
@ApiModel(value = "获取装修报价返回信息")
public class QuotePriceResponse {

    @ApiModelProperty("最小报价结果")
    private BigDecimal minDnaExpectPrice;

    @ApiModelProperty("报价结果ID")
    private Integer quotePriceId;

    @ApiModelProperty("报价详细信息")
    private List<QuotePriceDetailBean> quotePriceDetail;

    @Data
    @Accessors(chain = true)
    public static class QuotePriceDetailBean {

        @ApiModelProperty("风格名称")
        private String styleName;

        @ApiModelProperty("系列名称")
        private String seriesName;

        @ApiModelProperty("头图")
        private String headImgUrl;

        @ApiModelProperty("dnaId")
        private Integer dnaId;

        @ApiModelProperty("dna名称")
        private String dnaName;

        @ApiModelProperty("dna预计价格 单位元")
        private BigDecimal dnaExpectPrice;

        @ApiModelProperty("DNA类型:0软+硬，1纯软")
        private Integer dnaType;
    }
}
