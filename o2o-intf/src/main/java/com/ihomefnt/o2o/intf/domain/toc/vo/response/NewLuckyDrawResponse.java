package com.ihomefnt.o2o.intf.domain.toc.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/11/15 0015.
 */
@Data
@NoArgsConstructor
@ApiModel("新用户抽奖返回信息")
public class NewLuckyDrawResponse {

    @ApiModelProperty("剩余抽奖次数")
    private Integer times;
    @ApiModelProperty("奖品编号和需求文档中一致")
    private Integer prizeCode;
    @ApiModelProperty("奖项类型：0=免费赠送类，1=打折类，2=现金券类，3=注水符，4=现金红包类")
    private Integer type;
    @ApiModelProperty("奖品名称")
    private String prizeName;
    @ApiModelProperty("使用规则")
    private String msg;

    public NewLuckyDrawResponse(Integer times, Integer prizeCode, Integer type, String prizeName, String msg) {
        this.times = times;
        this.prizeCode = prizeCode;
        this.type = type;
        this.prizeName = prizeName;
        this.msg = msg;
    }
}
