package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@ApiModel("邀请人信息")
public class InviteDto {

    @ApiModelProperty("手机号")
    private String mobileNum;

    @ApiModelProperty("注册时间")
    private String registerTime;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户头像")
    private String headImgUrl;

    @ApiModelProperty("是否有效邀请 true为有效 false为无效 默认false")
    private boolean isValid = false;
}
