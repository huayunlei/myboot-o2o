package com.ihomefnt.o2o.intf.domain.bankcard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/2 0002.
 */
@Data
@ApiModel("银行卡、手机号,验证码校验")
public class CkeckPhoneCodeRequestVo  extends HttpBaseRequest {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("验证码类型")
    private int smsType = 2;    //1 注册  2 登录  3 重置;   设默认为2

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("卡号")
    private String cardNumber;

    @ApiModelProperty("身份证号")
    private String cardNo;
}
