package com.ihomefnt.o2o.intf.domain.promotion.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 * @author matao
 */
@Data
@ApiModel("促销活动请求参数")
public class MarketingActivityRequest extends HttpBaseRequest{
    @ApiModelProperty("活动ID")
    private Integer activityId;
    @ApiModelProperty("订单ID")
    private Integer orderId;
    @ApiModelProperty("是否已参加活动")
    private Boolean isIn;
    @ApiModelProperty("是否可以参加活动")
    private Boolean isAvailable;
}
