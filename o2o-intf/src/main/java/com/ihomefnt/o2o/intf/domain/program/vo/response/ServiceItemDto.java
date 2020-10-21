package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wanyunxin
 * @create 2019-09-04 16:18
 */
@NoArgsConstructor
@Data
@ApiModel("服务费信息")
public class ServiceItemDto {

    @ApiModelProperty(value = "skuId")
    private Integer skuId;

    /**
     * 商品类型
     */
    @ApiModelProperty(value = "商品类型")
    private Integer skuType;

    @ApiModelProperty("服务详述")
    private String skuDesc;

    @ApiModelProperty("商品名")
    private String skuName;

    @ApiModelProperty("图片")
    private String skuImage;

    @ApiModelProperty("末级类目ID（服务类型）")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称（服务类型名称）")
    private String lastCategoryName;

    @ApiModelProperty(value = "sku计价规则ID")
    private Integer skuPriceRuleId;

    @ApiModelProperty(value = "拆改方式：1.空间改造、2.新增墙体、3.拆除墙体")
    private Integer type;

    /**
     * 计量单位
     */
    @ApiModelProperty(value = "计量单位")
    private Integer unitType;
    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice = new BigDecimal(0);

    private BigDecimal marketPrice = new BigDecimal(0);
    private BigDecimal price = new BigDecimal(0);
    private BigDecimal purchasePrice = new BigDecimal(0);
    private Integer secondCategoryId;
    private String secondCategoryName;
    private Integer skuPriceRuleType;//算价规则类型 2固定报价 3算量报价
    private BigDecimal totalMarketPrice = new BigDecimal(0);
    private BigDecimal totalPriceX;
    private BigDecimal totalPurchasePrice = new BigDecimal(0);
    private String unitTypeName;
    private BigDecimal wallArea = new BigDecimal(0);
    private BigDecimal wallThickness;
    @ApiModelProperty("服务费描述")
    private String priceDesc;
}
