package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 响应给APP
 * @author jerfan cang
 * @date 2018/10/7 20:06
 */
@Data
public class RoomHardPackage {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品包ID")
    private Integer packageId;

    @ApiModelProperty("商品包名称")
    private String packageName;

    @ApiModelProperty("商品包描述")
    private String packageDesc;

    @ApiModelProperty("商品包图片")
    private String packageUrl;

    @ApiModelProperty("商品包前台分类")
    private String frontType;

    @ApiModelProperty("成本总价")
    private BigDecimal totalCost;

    @ApiModelProperty("售卖总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("用量")
    private BigDecimal quantities;

    @ApiModelProperty("是否默认")
    private Boolean defaultFlag;

    @ApiModelProperty("商品包下sku信息")
    private List<RoomPackageHardItem> roomHardSkuList;
}
