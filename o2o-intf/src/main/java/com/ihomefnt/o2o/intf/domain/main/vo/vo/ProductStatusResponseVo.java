package com.ihomefnt.o2o.intf.domain.main.vo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("商品状态查询返回")
public class ProductStatusResponseVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("客户订单号")
    private Long orderId;

    @ApiModelProperty("交付单号")
    private Long deliverId;

    @ApiModelProperty("交付商品唯一标识")
    private String deliverKey;

    @ApiModelProperty("商品关系")
    private Long relationId;

    @ApiModelProperty("订单商品唯一标识")
    private String superKey;

    @ApiModelProperty("商品id")
    private Long skuId;

    @ApiModelProperty("商品类型")
    private Integer skuType;

    @ApiModelProperty("商品名称")
    private String skuName;

    @ApiModelProperty("状态 1000待采购 1010未完成 3100待厂家接单 3200待厂家出货 3300厂家出货完成 5000待创建物流订单 5100待派物流单 5200待接单 5300待揽收 5400待发货 5600待到货 5700待预约 5800待签收 8888完结 9999已取消")
    private Integer status;

    @ApiModelProperty("复核状态 1复核中 2已复核")
    private Integer checkStatus;

    @ApiModelProperty("价格规则id")
    private Integer priceRuleId;

    @ApiModelProperty("价格规则类型")
    private Integer priceRuleType;

    @ApiModelProperty("商品头图")
    private String imgUrl;

    @ApiModelProperty("厂家型号")
    private String manufacturerModel;

    @ApiModelProperty("规格")
    private String specifications;

    @ApiModelProperty("售价")
    private BigDecimal price;

    @ApiModelProperty("采购单价")
    private BigDecimal purchasePrice;

    @ApiModelProperty("采购总价")
    private BigDecimal purchaseTotalPrice;

    @ApiModelProperty("长")
    private Integer length;

    @ApiModelProperty("宽")
    private Integer width;

    @ApiModelProperty("高")
    private Integer height;

    @ApiModelProperty("单件包裹件数")
    private BigDecimal packageCount;

    @ApiModelProperty("dr用量单位")
    private Integer unit;

    @ApiModelProperty("体积")
    private BigDecimal volume;

    @ApiModelProperty("重量")
    private BigDecimal weight;

    @ApiModelProperty("来源 1客户下单 2商品变更 3售后")
    private Integer source;

    @ApiModelProperty("供应商id")
    private Long supplierId;

    @ApiModelProperty("是否加急 0不加急 1加急")
    private Integer expeditedFlag;

    @ApiModelProperty("用量")
    private BigDecimal quantities;

    @ApiModelProperty("采购渠道  1：艾佳自采 2：施工单位自采")
    private Integer purchaseType;

    @ApiModelProperty("坑距")
    private Integer spacing;

    @ApiModelProperty("计算出的基础采购量，如17片地砖")
    private BigDecimal purchaseBaseQuantity;

    @ApiModelProperty("根据包装规格换算向上取整数，如20片地砖")
    private BigDecimal purchaseQuantity;

    @ApiModelProperty("包装单位 箱/包")
    private String packingUnitName;

    @ApiModelProperty("每包装单位的数量")
    private String packingSpecNum;

    @ApiModelProperty("厂家下单填写的采购量，如30片地砖")
    private BigDecimal actualPurchaseQuantity;

    @ApiModelProperty("空间Id")
    private Long roomId;

    @ApiModelProperty("空间")
    private String roomName;

    @ApiModelProperty("订单商品类型")
    private Integer orderType;

    @ApiModelProperty("采购数量（安装包装规格）")
    private BigDecimal purchaseNum;

    @ApiModelProperty("采购单位名称")
    private String purchaseUnitTypeName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否配件 0正常 1配件")
    private Integer additionalFlag = 0;

    @ApiModelProperty("采购指导价")
    private BigDecimal originalPurchasePrice;

    @ApiModelProperty("品牌")
    private String brandName;

    @ApiModelProperty("0不是退货 1重新发货退货 2订单发起退货")
    private Integer redispatchReturnFlag = 0;

    @ApiModelProperty("单位报价")
    private BigDecimal purchaseUnitPrice;

    @ApiModelProperty("总包裹数")
    private BigDecimal packageTotalCount;

    @ApiModelProperty("总体积")
    private BigDecimal totalVolume;

    @ApiModelProperty("总包数，目前是地板才用")
    private BigDecimal packageNum;
}
