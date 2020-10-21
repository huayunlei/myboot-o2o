package com.ihomefnt.o2o.intf.domain.collage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/16 18:40
 */
@Data
@ApiModel("JoinGroupRecordDto")
public class JoinGroupRecordDto {

    @ApiModelProperty("记录id")
    private Integer id;

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("团id")
    private Integer groupId;

    @ApiModelProperty("用户手机号")
    private String customerMobile;

    @ApiModelProperty("openId")
    private String customerOpenid;

    @ApiModelProperty("用户昵称")
    private String userNick;

    @ApiModelProperty("用户头像")
    private String headImg;

    @ApiModelProperty("入团时间")
    private  Long joinTime;

    @ApiModelProperty("团主标识 1 团主 0 团成员")
    private Integer ownFlag;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("记录创建时间")
    private Long createTime;

    @ApiModelProperty("记录修改时间")
    private Long updateTime;
}
