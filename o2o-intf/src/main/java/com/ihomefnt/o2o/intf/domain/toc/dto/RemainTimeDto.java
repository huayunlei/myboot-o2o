package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/11/21 0021.
 */
@Data
@ApiModel("老用户抽奖剩余次数")
public class RemainTimeDto {

    @ApiModelProperty("用户ID")
    private Integer newUserId;

    @ApiModelProperty("剩余次数")
    private Integer remainTimes;
}
