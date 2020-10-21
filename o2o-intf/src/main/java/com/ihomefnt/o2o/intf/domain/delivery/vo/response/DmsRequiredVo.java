package com.ihomefnt.o2o.intf.domain.delivery.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 交付需求确认信息返回对象
 * Author: ZHAO
 * Date: 2018年7月23日
 */
@Data
public class DmsRequiredVo {

    @ApiModelProperty("是否需要需求")
    private Boolean checkResult ;

    @ApiModelProperty("预计开工时间字符串")
    private String planBeginDateStr;

    @ApiModelProperty("需求确认记录数据")
    private List<DmsRequiredRecordVo> recordDtos;

}
