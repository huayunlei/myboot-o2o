package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/15 11:30
 */
@Data
@ApiModel("CollageMemberVo 团成员信息")
public class CollageMemberVo {

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("成员用户id，如果存在则返回，否则使用openId")
    private String userId;

    @ApiModelProperty("成员昵称")
    private String nickName;

    @ApiModelProperty("成员头像url")
    private String imgUrl;

    @ApiModelProperty("参与的团id")
    private String groupId;
}
