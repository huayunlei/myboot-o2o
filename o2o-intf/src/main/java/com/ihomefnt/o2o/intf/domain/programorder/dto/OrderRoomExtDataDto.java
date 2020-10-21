package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyonggang
 * @create 2019-06-13 10:24
 */
@ApiModel("订单空间扩展参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRoomExtDataDto {

    @ApiModelProperty("空间图片任务类型,0：不存在任务，1：正常渲染任务，2：失败渲染任务")
    private Integer taskType = 0;

    @ApiModelProperty("仅供参考提示 0 不提示 1提示 默认0")
    private Integer referenceOnlyFlag = 0;//仅供参考提示 0 不提示 1提示 默认0
}
