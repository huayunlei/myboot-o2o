package com.ihomefnt.o2o.intf.domain.agent.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liguolin
 * @create 2018-08-13 14:21
 **/
@Data
@ApiModel("b2c打款三要素二要素验证入参")
public class B2cValidationInfoRequestVo {

    @ApiModelProperty("银行卡号 银行卡打款验证三要素必传 不是银行卡打款非必传")
    private String cardNo;

    @ApiModelProperty("身份证 用于交税 必传")
    private String idCard;

    @ApiModelProperty("实名 必传")
    private String realName;
}
