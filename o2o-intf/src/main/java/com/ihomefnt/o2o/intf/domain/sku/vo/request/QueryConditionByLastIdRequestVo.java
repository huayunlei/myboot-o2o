package com.ihomefnt.o2o.intf.domain.sku.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("根据末级类目ID查询软装更多筛选条件入参")
@Data
public class QueryConditionByLastIdRequestVo {

    @ApiModelProperty("末级类目ID")
    private Integer lastCategoryId;

}
