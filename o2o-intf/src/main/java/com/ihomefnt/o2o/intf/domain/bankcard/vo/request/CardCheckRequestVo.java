package com.ihomefnt.o2o.intf.domain.bankcard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/2 0002.
 */
@Data
@ApiModel("银行卡校验")
public class CardCheckRequestVo extends HttpBaseRequest {

    @ApiModelProperty("卡号")
    private String cardNumber;

    @ApiModelProperty("用户姓名")
    private String name;
}
