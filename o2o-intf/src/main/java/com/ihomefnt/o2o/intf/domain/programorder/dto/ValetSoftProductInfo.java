package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 代客下单 软装清单商品信息
 *
 * @author ZHAO
 */
@Data
@ApiModel("软装清单商品信息")
public class ValetSoftProductInfo implements Serializable {

    @ApiModelProperty("商品ID")
    private Integer productId;//商品ID

    @ApiModelProperty("商品图片")
    private String headImgUrl;//商品图片

    @ApiModelProperty("商品图片")
    private String productName;//商品图片

    @ApiModelProperty("商品数量")
    private Integer productCount;//商品数量

    @ApiModelProperty("物流状态")
    private Integer deliveryStatus;//物流状态

    @ApiModelProperty("物流状态说明")
    private String deliveryStatusStr;//物流状态说明

    @ApiModelProperty("颜色")
    private String productColor;//颜色

    @ApiModelProperty("材质")
    private String productMaterial;//材质

    @ApiModelProperty("尺寸")
    private String productSize;//尺寸

    public ValetSoftProductInfo() {
        this.productId = -1;
        this.headImgUrl = "";
        this.productName = "";
        this.productCount = 0;
        this.deliveryStatus = -1;
        this.deliveryStatusStr = "";
        this.productColor = "";
        this.productMaterial = "";
        this.productSize = "";
    }
}
