package com.ihomefnt.o2o.intf.domain.weixin.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/10/12
 */
@Data
@ApiModel("微信用户信息请求")
public class UserInfoRequest {

    @ApiModelProperty("微信code")
    private String code;
}
