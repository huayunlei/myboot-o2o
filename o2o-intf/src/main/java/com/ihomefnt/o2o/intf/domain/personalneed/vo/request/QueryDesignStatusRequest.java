package com.ihomefnt.o2o.intf.domain.personalneed.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/24 10:41 上午
 */
@ApiModel("QueryDesignStatusRequest")
@Data
public class QueryDesignStatusRequest extends HttpBaseRequest {

    @ApiModelProperty("订单Id")
    private Integer orderId;

}
