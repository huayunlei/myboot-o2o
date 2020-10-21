package com.ihomefnt.o2o.intf.domain.sku.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("待展示筛选项")
@Data
public class NeedDisplayPropertyDto {
    //bannerPropertyId 控制外层的显示
    @ApiModelProperty("首层筛选 -1:categoryList;-2:brandList 其它：propertyList.propertyId")
    private Long bannerPropertyId;

    //needDisplayPropertyIds 控制筛选内的内容显示
    @ApiModelProperty("需要展示的分类ID集合(-1:categoryList;-2:brandList 其它：propertyList.propertyValueList）")
    private List<Long> needDisplayPropertyIds;

}
