package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 需求确认记录数据
 * Author: ZHAO
 * Date: 2018年7月23日
 */
@Data
public class DmsRequiredRecordVo {

    @ApiModelProperty("需求确认时间")
    private String dateStr;

    @ApiModelProperty("需求确认人姓名")
    private String operateName;

    @ApiModelProperty("需求确认人渠道")
    private String operateSource;
}
