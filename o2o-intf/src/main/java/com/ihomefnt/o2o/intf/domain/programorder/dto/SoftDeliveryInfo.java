package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 软装配送信息
 *
 * @author ZHAO
 */
@Data
@ApiModel("软装配送信息")
public class SoftDeliveryInfo implements Serializable {

    @ApiModelProperty("软装订单id")
    private Integer softOrderId;// 软装订单id

    @ApiModelProperty(" 软装订单状态0：待付款，1：待采购，2：采购中，3：待配送，4：配送中，5：已完成，6：已取消")
    private Integer softOrderStatus;// 软装订单状态0：待付款，1：待采购，2：采购中，3：待配送，4：配送中，5：已完成，6：已取消

    @ApiModelProperty("软装订单状态字符串")
    private String softOrderStatusStr;// 软装订单状态字符串

    @ApiModelProperty("软装订单状态文案")
    private String softorderStatusPraise;//软装订单状态文案

    @ApiModelProperty("商品集合")
    private List<ValetSoftProductInfo> valetSoftProductInfoList;//商品集合

    @ApiModelProperty("商品总数")
    private Integer productTotalCount;//商品总数

    public SoftDeliveryInfo() {
        this.softOrderId = -1;
        this.softOrderStatus = -1;
        this.softOrderStatusStr = "";
        this.softorderStatusPraise = "";
        this.valetSoftProductInfoList = new ArrayList<>();
        this.productTotalCount = 0;
    }
}
