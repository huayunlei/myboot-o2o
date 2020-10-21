package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/11/16 0016.
 */
@ApiModel("奖品信息")
@Data
@NoArgsConstructor
public class PrizeDto {

    @ApiModelProperty("礼品序号")
    private  Integer prizeNo;

    @ApiModelProperty("领取时间")
    private  String receiveTime;

    @ApiModelProperty("状态 1已抽奖 2待兑现 3兑现中 4 已兑现 5 待回收 6 已回收 7已作废")
    private  Integer state;

    @ApiModelProperty("使用规则说明")
    private  String msg;

    @ApiModelProperty("福利名称")
    private  String prizeName;

    @ApiModelProperty("奖项类型：0=免费赠送类，1=打折类，2=现金券类，3=注水符，4=现金红包类")
    private Integer type;

    public PrizeDto(Integer prizeNo, String receiveTime, Integer state, String msg, String prizeName,Integer type) {
        this.prizeNo = prizeNo;
        this.receiveTime = receiveTime;
        this.state = state;
        this.msg = msg;
        this.prizeName = prizeName;
        this.type = type;
    }
}
