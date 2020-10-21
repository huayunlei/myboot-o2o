package com.ihomefnt.o2o.intf.domain.main.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */

@ApiModel("确认方案信息")
@Data
@Accessors(chain = true)
public class ConfirmSolutionInfo {

    @ApiModelProperty("是否已确认方案")
    private Boolean isConfirmed;

    @ApiModelProperty("确认开工状态：0-未达到确认开工条件 1-需要确认开工 2-已经确认开工")
    private Integer confirmConstructionStatus;

    @ApiModelProperty("需求确认时间")
    private String confirmationTime;

    @ApiModelProperty("距离交房时间")
    private Integer leaveRoomDays;

    @ApiModelProperty("顶部文案提示")
    private Integer topTips;

}
