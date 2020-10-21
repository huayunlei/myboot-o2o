package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author liguolin
 * @time 2017-06-17 14:29:57
 */
@Data
@ApiModel("快照空间下sku的信息")
public class SnapshotOrderRoomSkuInfo {
	
	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	private Long id;

	/**
	 * 大订单num
	 */
	@ApiModelProperty("大订单num")
    private Long orderNum;

	/**
	 * 子订单id
	 */
	@ApiModelProperty("子订单id")
    private Long childOrderId;

	/**
	 * 空间id
	 */
	@ApiModelProperty("空间id")
    private Long roomId;

	/**
	 * sku_id
	 */
	@ApiModelProperty("skuId")
    private Integer skuId;

	/**
	 * 商品名称
	 */
	@ApiModelProperty("商品名称")
    private String productName;

	/**
	 * 商品头图
	 */
	@ApiModelProperty("商品头图")
    private String productImage;

	/**
	 * 商品单价
	 */
	@ApiModelProperty("商品单价")
    private BigDecimal productPrice;

	/**
	 * 商品数量
	 */
	@ApiModelProperty("商品数量")
    private Integer productCount;

	/**
	 * 商品总价
	 */
	@ApiModelProperty("商品总价")
    private BigDecimal productTotalAmount;

	/**
	 * 家具类型 取值：0成品家具，1定制家具，2赠品家具
	 */
	@ApiModelProperty("家具类型 取值：0成品家具，1定制家具，2赠品家具")
    private Integer furnitureType;

	/**
	 * 类目id
	 */
	@ApiModelProperty("类目id")
    private Integer productCategory;

	/**
	 * 类目名称
	 */
	@ApiModelProperty("类目名称")
    private String categoryName;

	/**
	 * 品牌id
	 */
	@ApiModelProperty("品牌id")
    private Integer brandId;

	/**
	 * 品牌名称
	 */
	@ApiModelProperty("品牌名称")
    private String brand;

	/**
	 * 风格id
	 */
	@ApiModelProperty("风格id")
    private Integer styleId;

	/**
	 * 风格名称
	 */
	@ApiModelProperty("风格名称")
    private String style;

	/**
	 * 材质
	 */
	@ApiModelProperty("材质")
    private String material;

	/**
	 * 市场价
	 */
	@ApiModelProperty("市场价")
    private BigDecimal marketPrice;

	/**
	 * 长
	 */
	@ApiModelProperty("长")
    private Integer length;

	/**
	 * 宽
	 */
	@ApiModelProperty("宽")
    private Integer width;

	/**
	 * 高
	 */
	@ApiModelProperty("高")
    private Integer height;

	/**
	 * 体积
	 */
	@ApiModelProperty("体积")
    private Integer volume;

	/**
	 * 是否为样品
	 */
	@ApiModelProperty("是否为样品")
    private Integer sample;
	
	/**
	 * 商品序号
	 */
	@ApiModelProperty("商品序号")
	private Integer sequenceNum;
	
	/**
	 * 商品颜色
	 */
	@ApiModelProperty("商品颜色")
	private String productColor;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
    private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
    private Date updateTime;

	/**
	 * 删除标识0：未删除1;已删除
	 */
	@ApiModelProperty("删除标识0：未删除1;已删除")
    private Byte delFlag;
}
