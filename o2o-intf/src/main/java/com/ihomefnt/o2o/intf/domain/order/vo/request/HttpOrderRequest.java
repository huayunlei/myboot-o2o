package com.ihomefnt.o2o.intf.domain.order.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 */
@ApiModel("订单详情")
@Data
public class HttpOrderRequest extends HttpBaseRequest{

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("支付金额-分笔")
    private double selectSum;

    @ApiModelProperty("微信支付openid")
    private String openId;

    @ApiModelProperty("订单类型   1 软装类型   2  硬装类型   3  全品家类型   5 艺术品类型 17 画屏订单")
    private Integer orderType;

    @ApiModelProperty("订单状态")
    private Integer state;

    @ApiModelProperty("售卖类型 0默认 1定制商品")
    private Integer saleType;


}
