package com.ihomefnt.o2o.intf.domain.product.dto;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品sku属性")
public class SkuVo {
	
	@ApiModelProperty("主键")
	private Integer id; //(integer, optional): 主键,
	@ApiModelProperty("")
	private String skuPvs; //(string, optional),
	@ApiModelProperty("库存")
	private Integer stock; //(integer, optional): 库存,
	@ApiModelProperty("采购价")
	private BigDecimal purchasePrice; //(number, optional): 采购价,
	@ApiModelProperty("艾佳售价")
	private BigDecimal aijiaPrice; //(number, optional): 艾佳售价,
	@ApiModelProperty("市场价/原价")
	private BigDecimal marketPrice; //(number, optional): 市场价,
	@ApiModelProperty("长")
	private Integer longth; //(integer, optional): 长,
	@ApiModelProperty("宽")
	private Integer width; //(integer, optional): 宽,
	@ApiModelProperty("高")
	private Integer height; //(integer, optional): 高,
	@ApiModelProperty("货品id")
	private List<CargoVo> cargoes; //(array[CargoVo], optional): 货品id,
	@ApiModelProperty("图片")
	private List<String> images; //(array[string], optional): 图片,
	@ApiModelProperty("透明图")
	private List<String> graphs;// (array[string], optional): 透明图,
	@ApiModelProperty("3D模型")
	private String model; //(string, optional);// 3d模型
}
