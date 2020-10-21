package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/12/6 0006.
 */
@Data
@ApiModel("解绑参数")
public class UnbindFacilityDto extends HttpBaseRequest {

    @ApiModelProperty("设备id")
    private Integer facilityId;

    @ApiModelProperty("操作人ID")
    private Integer operator;

    @ApiModelProperty("绑定用户 ID")
    private Integer userId;
}
