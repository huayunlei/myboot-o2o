package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderSoftDetailResultSimpleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "软装清单查询返回")
public class SoftListResponse {

    @ApiModelProperty("软装数量")
    private Integer softTotal;

    @ApiModelProperty("软装完成数")
    private Integer softFinishNum;

    @ApiModelProperty(value = "软装详细信息")
    private OrderSoftDetailResultSimpleDto orderSoftDetailResultDto;

}
