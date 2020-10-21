package com.ihomefnt.o2o.intf.domain.activity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/11/26
 */
@Data
@Accessors(chain = true)
@ApiModel("邀请码信息")
public class InviteCodeDto {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("所有者手机号")
    private String mobile;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("是否有效:0-有效，-1-失效")
    private Integer validStatus;
}
