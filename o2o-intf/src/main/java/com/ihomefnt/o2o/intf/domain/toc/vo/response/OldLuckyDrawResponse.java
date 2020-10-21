package com.ihomefnt.o2o.intf.domain.toc.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("老用户抽奖返回信息")
public class OldLuckyDrawResponse {

    @ApiModelProperty("剩余抽奖次数")
    private Integer times;

    @ApiModelProperty("现金金额")
    private Integer money;

    @ApiModelProperty("现金总额")
    private Integer moneyTotal;


    public OldLuckyDrawResponse(Integer times, Integer money, Integer moneyTotal) {
        this.times = times;
        this.money = money;

        this.moneyTotal = moneyTotal;
    }
}
