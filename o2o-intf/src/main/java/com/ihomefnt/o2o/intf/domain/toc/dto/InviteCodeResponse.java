package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@ApiModel("邀请码信息")
public class InviteCodeResponse {

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("用户真实姓名")
    private String name;

    @ApiModelProperty("是否个人经纪人 true是 false 否")
    private Boolean isPersonalAgent = true;

    @ApiModelProperty("提示信息")
    private String inviteCodeMessage;
}
