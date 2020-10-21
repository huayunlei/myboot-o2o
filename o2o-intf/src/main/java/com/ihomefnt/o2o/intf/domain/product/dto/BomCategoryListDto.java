package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("APP组合分类信息ListDTO")
public class BomCategoryListDto {

    List<BomCategoryDTO> groupCategoryList;
}
