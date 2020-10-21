package com.ihomefnt.o2o.intf.domain.culture.vo.response;

import com.ihomefnt.o2o.intf.domain.program.dto.ImageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 文旅商品
 * @author Charl
 */
@Data
@ApiModel("文旅商品vo")
public class CultureCommodityResponseVo {
	
	@ApiModelProperty("商品id")
	private int itemId; //商品id
	
	@ApiModelProperty("商品名称")
	private String itemName; //商品名称
	
	@ApiModelProperty("商家id")
	private int sellerId; //商家id
	
	@ApiModelProperty("商家名称")
	private String sellerName; //商家名称
	
	@ApiModelProperty("商品类型")
	private int itemTypeId; //商品类型id
	
	@ApiModelProperty("商品类型名称")
	private String itemType; //文旅商品类型：餐饮类、旅游类、服务类
	
	@ApiModelProperty("商品头图")
	private String itemHeadImg; //商品头图
	
	@ApiModelProperty("商品售价")
	private BigDecimal itemSellPrice; //商品售价
	
	@ApiModelProperty("商品原价")
	private BigDecimal itemOriginPrice; //商品原价
	
	@ApiModelProperty("商家地址")
	private String sellerAddress; //商家地址
	
	@ApiModelProperty("图文详情(大文本)")
	private String sellPointContent; //商品图文(大文本)

	@ApiModelProperty("商家所在区域")
	private long region; //商品所在区域id（210000南京、100000北京、450000郑州）
	
	@ApiModelProperty("区域名称")
	private String regionName; //区域名称
	
	@ApiModelProperty("商品库存")
	private Integer stock;
	
	private ImageEntity headImgObj; //文旅商品大小图
}
