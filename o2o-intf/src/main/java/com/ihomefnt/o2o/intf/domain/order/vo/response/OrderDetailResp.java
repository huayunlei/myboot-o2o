package com.ihomefnt.o2o.intf.domain.order.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("OrderDetailResp")
@Data
public class OrderDetailResp {

    /**
    *套装id
    */
    @ApiModelProperty("套装id")
    private Integer fidSuit;
    /**
    *空间id
    */
    @ApiModelProperty("空间id")
    private Integer fidSuitRoom;
    /**
    *商品id
    */
    @ApiModelProperty("商品id")
    private Integer fidProduct;
    /**
    *商品数量
    */
    @ApiModelProperty("商品数量")
    private Integer productAmount;
    
    /**
     * 是否已评价 的 true表示已评价
     */
    @ApiModelProperty("是否已评价 的 true表示已评价")
    private boolean commentResultTag = false;
    
    /**
     * 
     * 预计发货时间的倒计时[艺术品才有]
     */
    @ApiModelProperty("预计发货时间的倒计时[艺术品才有]")
    private Integer deliveryTime;
    
    /**
    *商品总价
    */
    @ApiModelProperty("商品总价")
    private BigDecimal productAmountPrice;

    @ApiModelProperty("商品名称")
    private String productName; //商品名称

    @ApiModelProperty("商品头图")
    private String headImage;   //商品头图

    @ApiModelProperty("商品类型")
	private Integer productCategoty;  //商品类型

    @ApiModelProperty("商品单价")
	private BigDecimal productPrice;  //商品单价
	
    private String artName;   //商品头图
    private String selectAttr;   //用户选择属性

    @ApiModelProperty("新艾商城属性")
    private String propertyNameValue;

    private String markWord;   //配文信息


    @ApiModelProperty("定制内容")
    private String customizedContent;

    @ApiModelProperty("新版艾商城skuid")
    private String skuId;

    @ApiModelProperty("作品或商品 为0 取fidProduct 其他取skuId")
    private Integer skuType;

    @ApiModelProperty(value = "艺术品id")
    private String worksId;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    private Boolean showFlag = false;


}
