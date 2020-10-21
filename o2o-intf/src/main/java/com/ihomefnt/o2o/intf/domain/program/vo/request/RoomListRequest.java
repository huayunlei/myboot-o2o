package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */
@Data
@ApiModel("方案可选空间替换列表入参")
public class RoomListRequest extends HttpBaseRequest{

    @ApiModelProperty("户型id")
    private Integer apartmentId;

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("已选方案id")
    private Integer solutionId;

}
