package com.ihomefnt.o2o.intf.domain.agent.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liguolin
 * @create 2018-08-13 14:28
 **/
@Data
@ApiModel("b2c打款三要素二要素验证结果")
public class B2cValidationResult {

    @ApiModelProperty("是否通过验证")
    private boolean isPassCheck;

    @ApiModelProperty("验证描述")
    private String message;
}
