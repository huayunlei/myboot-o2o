package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("根据类目查询筛选条件返回")
public class QueryConditionByCategoryDto {


    @ApiModelProperty("二级类目")
    private CategoryDto secondCategory;

    @ApiModelProperty("类目集合")
    private List<CategoryDto> categoryList;

    @ApiModelProperty("品牌")
    private List<BrandDto> brandList;

    @ApiModelProperty("其余属性集合")
    private List<ProductPropertyDto> propertyList;

}
