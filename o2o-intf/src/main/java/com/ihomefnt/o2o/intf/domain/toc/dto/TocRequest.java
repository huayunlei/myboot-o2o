package com.ihomefnt.o2o.intf.domain.toc.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@ApiModel("toc请求参数")
public class TocRequest extends HttpBaseRequest {

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("被邀请人手机号")
    private String inviterMobile;

    @ApiModelProperty("验证码")
    private String smsCode;
}
