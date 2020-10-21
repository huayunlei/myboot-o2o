package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("抽奖资格和次数")
public class LuckyDrawTimeResponse {

    @ApiModelProperty("新用户抽奖资格 true有资格 false无资格")
    private Boolean qualified;

    @ApiModelProperty("剩余抽奖次数")
    private Integer times;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("经纪人手机号")
    private String mobile;

    public LuckyDrawTimeResponse(Boolean qualified, Integer times) {
        this.qualified = qualified;
        this.times = times;
    }
}
