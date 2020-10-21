package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@ApiModel(description = "硬装增减项记录详情")
public class IncrementItemHardDetail {

    private Long id;

    @ApiModelProperty(value = "大订单Num")
    private Long orderNum;
    @ApiModelProperty(value = "申请批次")
    private Long batchId;
    @ApiModelProperty(value = "硬装金额")
    private BigDecimal hardAmount;
    @ApiModelProperty(value = "增减项标识")
    private Integer incrementFlag;
    @ApiModelProperty(value = "类目说明")
    private String itemDesciption;
    @ApiModelProperty(value = "计量单位")
    private String unit;
    @ApiModelProperty(value = "类目数量")
    private Integer itemCount;
    @ApiModelProperty(value = "实际金额")
    private BigDecimal actualAmount;
    @ApiModelProperty(value = "成本金额")
    private BigDecimal costAmount;
    @ApiModelProperty(value = "是否是当前硬装公司")
    private Short isCurrentCompany;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "删除标记位")
    private Integer delFlag;
    @ApiModelProperty(value = "操作人")
    private Long operator;

    @ApiModelProperty(value = "减项老的批次ID")
    private Long oldBatchId;

    @ApiModelProperty(value = "格式化申请时间时间")
    private String applyTimeStr;

    @ApiModelProperty(value = "操作人字符串")
    private String operatorStr;

    @ApiModelProperty(value = "类别id")
    private Long categoryId;

    @ApiModelProperty(value = "类别名称")
    private String categoryName;

    @ApiModelProperty(value = "空间id")
    private Long roomId;

    @ApiModelProperty(value = "空间名称")
    private String roomName;

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "spuId")
    private Long spuId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "图片")
    private String productImge;

    @ApiModelProperty(value = "商品规格")
    private String specifications;

    @ApiModelProperty(value = "方案必选项 0:否 1:是")
    private Integer isRequired;

    @ApiModelProperty(value = "属性 3.标准升级项 4.非标准升级项 5.硬装增配包")
    private Integer propertyType;

    @ApiModelProperty("唯一标识")
    private String superKey;
}