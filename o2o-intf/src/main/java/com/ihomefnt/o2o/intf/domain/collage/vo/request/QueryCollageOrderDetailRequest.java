package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/19 21:31
 */
@Data
@ApiModel("订单操作")
public class QueryCollageOrderDetailRequest extends HttpBaseRequest {

    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    @ApiModelProperty(value = "openid",hidden = true)
    private String openid;


    @ApiModelProperty(value = "订单id集合",hidden = true)
    private List<Integer> orderIds;
}
