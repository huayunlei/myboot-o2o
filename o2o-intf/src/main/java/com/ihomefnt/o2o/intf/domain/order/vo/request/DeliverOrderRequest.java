package com.ihomefnt.o2o.intf.domain.order.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-07 15:03
 */
@ApiModel("订单列表物流信息入参")
@Data
@SuppressWarnings("all")
public class DeliverOrderRequest extends HttpBaseRequest {

    @ApiModelProperty(value = "订单状态")
    private Integer state;

    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    @ApiModelProperty(value = "快递编号")
    private String logisticnum;

    @ApiModelProperty(value = "快递快递公司编号")
    private String logisticcompanycode;

    @ApiModelProperty(value = "快递快递公司名称")
    private String logisticcompanyname;
}
