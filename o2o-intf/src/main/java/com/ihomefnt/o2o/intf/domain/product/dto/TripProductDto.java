package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("文旅商品vo")
public class TripProductDto {
	
	@ApiModelProperty("主键")
	private Integer id;//(integer, optional): 主键,
	@ApiModelProperty("草稿id")
	private String mongoId;// (string, optional): 草稿id,
	@ApiModelProperty("sku id")
	private Integer skuId; // (integer, optional): skuId,
	@ApiModelProperty("模版id")
	private Integer templateId; // (integer, optional): 模板id,
	@ApiModelProperty("品类id")
	private Integer categoryId; // (integer, optional): 品类id,
	@ApiModelProperty("商品名称")
	private String productName;// (string, optional): 商品名称,
	@ApiModelProperty("品牌id")
	private Integer brandId; // (integer, optional): 品牌id,
	@ApiModelProperty("风格id")
	private Integer styleId; // (integer, optional): 风格id,
	@ApiModelProperty("图文详情")
	private String graphicDetails; // (string, optional): 图文详情,
	@ApiModelProperty("spu属性")
	private List<ProductPropertyValuesVo> productSpuPropertyList; // (array[ProductPropertyValuesVo], optional): spu属性,
	@ApiModelProperty("sku属性")
	private List<ProductPropertyValuesVo> productSkuPropertyList; //(array[ProductPropertyValuesVo], optional): sku属性,
	@ApiModelProperty("sku体")
	private List<SkuVo> skuList; // (array[SkuVo], optional): sku体
	@ApiModelProperty("状态 0：待上架；1：上架")
	private Integer status;
	@ApiModelProperty("是否删除状态")
	private Integer deleted;
}
