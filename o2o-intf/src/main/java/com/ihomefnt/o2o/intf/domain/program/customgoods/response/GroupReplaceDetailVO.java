package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ihomefnt.o2o.intf.domain.program.vo.response.QueryCabinetPropertyListResponseNew;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liyonggang
 * @create 2019-03-20 21:05
 */
@Data
@ApiModel("组合替换数据")
public class GroupReplaceDetailVO {


    @ApiModelProperty("默认组合id")
    private Integer defaultGroupId;

    @ApiModelProperty(value = "默认组合图片",hidden = true)
    @JsonIgnore
    private String defaultGroupImage;

    @ApiModelProperty("默认组合名称")
    private String defaultGroupName;

    @ApiModelProperty("替换组合名称")
    private String replaceGroupName;

    @ApiModelProperty("组合默认图片集合")
    private List<ImageVO> defaultGroupImageList;

    @ApiModelProperty("替换组合id")
    private Integer replaceGroupId;

    @ApiModelProperty(value = "替换组合图片",hidden = true)
    @JsonIgnore
    private String replaceGroupImage;

    @ApiModelProperty("组合替换图片集合")
    private List<ImageVO> replaceGroupImageList;

    @ApiModelProperty("组件集合")
    private List<ComponentList> componentList;

    @ApiModelProperty("提示选配免费商品文案")
    private String freeMessage;

    @ApiModelProperty("默认窗帘沙价格")
    private BigDecimal defaultYarnPrice;

    @ApiModelProperty("默认组合中的窗帘纱价格（仅在默认组合中包含窗帘纱&freeAble=1时有返回）")
    private Integer defaultYarnComponentId;


    @Data
    public static class ComponentList {

        @ApiModelProperty("组件id")
        private Integer componentId;

        @ApiModelProperty("物料分类id")
        private String componentCategoryName;

        @ApiModelProperty("组件分类id")
        private Integer componentCategoryId;

        @ApiModelProperty("组件数量")
        private Integer componentNum;

        @ApiModelProperty("APP组合物料替换详情信息DTO")
        private Material defaultMaterial;

        @ApiModelProperty("组件用量")
        private BigDecimal quantities;

        @ApiModelProperty("APP组合物料替换详情信息DTO")
        private Material replaceMaterial;

        @ApiModelProperty("是否可选配物料")
        private Boolean replaceMaterialFlag;

        @ApiModelProperty("差价")
        private BigDecimal priceDifferences;

        @ApiModelProperty(value = "逻辑使用字段",hidden = true)
        @JsonIgnore
        QueryCabinetPropertyListResponseNew.PropertyDataDto propertyDataDto;



        @Data
        @ApiModel("物料信息")
        public static class Material {

            @ApiModelProperty("物料品牌id")
            private Integer brandId;

            @ApiModelProperty("物料品牌名称")
            private String brandName;

            @ApiModelProperty("物料分类id")
            private Integer materialCategoryId;

            @ApiModelProperty("物料id")
            private Integer materialId;

            @ApiModelProperty(value = "物料图片",hidden = true)
            @JsonIgnore
            private String materialImage;

            @ApiModelProperty("物料图片")
            private List<ImageVO> materialImageList;

            @ApiModelProperty("价格")
            private BigDecimal price;

            @ApiModelProperty("1 是免费赠品 0 非免费赠品")
            private Integer freeFlag = 0;

            @ApiModelProperty(" 0 不展示 1可替换为赠品 2免费赠品 4效果图推荐")//免费赠品优先级高于效果图推荐
            private Integer showFreeFlag = 0;

        }
    }
}
