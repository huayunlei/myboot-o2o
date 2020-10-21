package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户绑定信息")
public class BindSuccesVo {

    @ApiModelProperty(value = "绑定状态 0:第一次主绑定,1:第二次主绑定,2:普通用户绑定")
    private Integer bindState;

    @ApiModelProperty(value = "处理结果")
    private boolean result;

}
