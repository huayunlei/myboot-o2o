package com.ihomefnt.o2o.intf.domain.sku.vo.response;

import com.ihomefnt.o2o.intf.domain.product.dto.QueryConditionByCategoryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("根据末级类目ID查询软装更多筛选条件返回")
@Data
public class QueryConditionByLastIdResponseVo extends QueryConditionByCategoryDto {

    @ApiModelProperty("待展示筛选项")
    private NeedDisplayPropertyDto needDisplayProperties;

    @ApiModelProperty("前端标题")
    private String pageTitle;
}
