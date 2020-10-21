package com.ihomefnt.o2o.intf.domain.program.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-05 15:31
 */
@Data
@ApiModel("方案意见分页查询参数")
@Accessors(chain = true)
public class ProgramOpinionPageQueryReq {
    @ApiModelProperty("页号")
    private Integer pageNo;

    @ApiModelProperty("每页记录数")
    private Integer pageSize;
}
