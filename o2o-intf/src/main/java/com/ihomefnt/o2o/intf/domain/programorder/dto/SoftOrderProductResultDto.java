package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lindan on 2017/8/3.
 */
@Data
@ApiModel("商品信息参数")
public class SoftOrderProductResultDto {

    @ApiModelProperty("skuId")
    private Integer skuId;

    @ApiModelProperty("图片路径")
    private String imageUrl;

    @ApiModelProperty("spuId")
    private Integer spuId;

    @ApiModelProperty("批次号")
    private Long batchId;

    @ApiModelProperty(value = "订单类型")
    private Integer detailType;

    @ApiModelProperty("spu名称")
    private String name;

    @ApiModelProperty("品牌&系列名称")
    private String brandAndSeries;

    @ApiModelProperty("商品规格")
    private String specifications ;

    @ApiModelProperty("样品,0：不是，1：是")
    private Integer sample;

    @ApiModelProperty("单价")
    private BigDecimal aijiaLowerPrice;

    @ApiModelProperty("艾佳最高售价")
    private BigDecimal aijiaUpperPrice;

    @ApiModelProperty("数量")
    private Integer amount;

    @ApiModelProperty("金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("实际金额")
    private BigDecimal actualTotalAmount;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("商品状态")
    private Integer productStatus;

    @ApiModelProperty("商品状态字符串")
    private String productStatusStr;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否可被移除")
    private Boolean canBeRemove;

    @ApiModelProperty("申请时间格式化")
    private String applyTimeStr;

    @ApiModelProperty("SKU 规格信息")
    private String pvsInfo;

    @ApiModelProperty("艾佳最低采购单价")
    private BigDecimal purchaseLowerPrice;

    @ApiModelProperty("艾佳最高采购单价")
    private BigDecimal purchaseUpperPrice;

    @ApiModelProperty("SKU 长")
    private Double length;

    @ApiModelProperty("SKU 宽")
    private Double width;

    @ApiModelProperty("SKU 高")
    private Double height;

    @ApiModelProperty("销售单价")
    private BigDecimal sellUnitPrice;

    @ApiModelProperty("商品标志 1-定制窗帘")
    private Integer mark;

    @ApiModelProperty("商品类型")
    private Integer type;

    @ApiModelProperty("商品类型字符串")
    private String furnitureTypeStr;

    @ApiModelProperty("唯一标识")
    private String superKey;

    @ApiModelProperty("创建时间")
    private String createTimeStr;
    
    @ApiModelProperty("四级类目id")
    private Integer productCategory;

    @ApiModelProperty("四级类目名称")
    private String categoryName;

    @ApiModelProperty("末级类目id")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;
    
}
