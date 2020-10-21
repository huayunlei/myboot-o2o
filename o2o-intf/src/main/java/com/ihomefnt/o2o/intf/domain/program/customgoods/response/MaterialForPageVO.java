package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liyonggang
 * @create 2019-03-19 15:02
 */
@ApiModel("物料列表")
@Data
public class MaterialForPageVO {

    @ApiModelProperty("当前页")
    private Integer current;
    @ApiModelProperty("页数")
    private Integer pages;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Boolean searchCount = Boolean.TRUE;
    @ApiModelProperty("页长")
    private Integer size;
    @ApiModelProperty("总数")
    private Integer total;
    @ApiModelProperty("物料列表")
    private List<RecordsBean> records;

    @Data
    @ApiModel("物料信息记录")
    public static class RecordsBean {

        @ApiModelProperty(value = "物料头图",hidden = true)
        @JsonIgnore
        private String materialImage;
        @ApiModelProperty("头图图片集合")
        private List<ImageVO> banners;
        @ApiModelProperty("品牌id")
        private Integer brandId;
        @ApiModelProperty("物料id")
        private Integer materialId;
        @ApiModelProperty("物料名称")
        private String materialName;
        @ApiModelProperty("差价")
        private BigDecimal priceDifferences;
        @ApiModelProperty("品牌名称")
        private String brandName;
        @ApiModelProperty("1 是免费赠品 0 非免费赠品")
        private Integer freeFlag = 0;
        @ApiModelProperty(" 0 不展示 1可替换为赠品 2免费赠品 4效果图推荐")//免费赠品优先级高于效果图推荐
        private Integer showFreeFlag = 0;
    }

}
