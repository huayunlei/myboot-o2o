package com.ihomefnt.o2o.intf.domain.weixin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/10/12
 */

@ApiModel("微信用户信息返回")
@Data
public class WeChatUserInfo {

    @ApiModelProperty("用户openid")
    private String openid;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户性别")
    private String sex;

    @ApiModelProperty("用户头像")
    private String headimgurl;

    @ApiModelProperty("用户unionid")
    private String unionid;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty(value = "地区")//省份-城市
    private String location;

}
