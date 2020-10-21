package com.ihomefnt.o2o.intf.domain.order.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("公共请求参数")
public class HttpOrderListRequest extends HttpBaseRequest {

    private int pageSize;
    private int pageNo;
    
    @ApiModelProperty("订单渠道  ： 1 艾商城，2 小星星小程序")
	private Integer orderChannel;
}
