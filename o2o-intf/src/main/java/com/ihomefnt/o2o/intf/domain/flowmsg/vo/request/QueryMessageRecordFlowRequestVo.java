package com.ihomefnt.o2o.intf.domain.flowmsg.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-20 19:24
 */
@Data
@Accessors(chain = true)
@ApiModel("查询信息流请求参数")
public class QueryMessageRecordFlowRequestVo extends HttpBaseRequest {
    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("页号")
    private Integer pageNo = 1;

    @ApiModelProperty("每页记录数")
    private Integer pageSize = 10;
}
