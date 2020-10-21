package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 区分新老用户
 *
 * @author liyonggang
 * @create 2018-11-19 17:18
 */
@Data
@Accessors(chain = true)
@ApiModel("新老用户区分")
public class UserDistinguishDto implements Serializable {

    private static final long serialVersionUID = 693258940284511598L;

    @ApiModelProperty("是否是老用户  true:老用户,false新用户")
    private boolean old;
    @ApiModelProperty("剩余抽奖次数,新用户时有值")
    private Integer residueNum;
    @ApiModelProperty("是否已经绑定个人经纪人")
    private boolean binding = false;
    @ApiModelProperty("经纪人手机号")
    private String mobile;
}
