package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@ApiModel("老用户抽到的现金")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MoneyDto {

    @ApiModelProperty("手机号")
    private String mobileNum;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("领取时间")
    private String receiveTime;

    @ApiModelProperty("用户头像")
    private String headImgUrl;

    @ApiModelProperty("金额")
    private Integer money;

    @ApiModelProperty("状态 1=已抽奖，2=待兑现，3=兑现中，4=已兑现，5=待回收，6=已回收，7=已作废")
    private Integer state;

    @ApiModelProperty("领取详情")
    private String msg;

    @ApiModelProperty("奖项类型：0=免费赠送类，1=打折类，2=现金券类，3=注水符，4=现金红包类")
    private Integer type;

    public MoneyDto(Integer userId, String receiveTime, Integer money, Integer state,Integer type) {
        this.userId = userId;
        this.receiveTime = receiveTime;
        this.money = money;
        this.state = state;
        this.type = type;
    }
}
