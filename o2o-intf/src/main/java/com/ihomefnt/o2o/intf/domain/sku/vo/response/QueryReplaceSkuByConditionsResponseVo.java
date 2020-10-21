package com.ihomefnt.o2o.intf.domain.sku.vo.response;

import com.ihomefnt.o2o.intf.domain.product.dto.SearchReplaceSkuDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("搜索同类SKU返回")
public class QueryReplaceSkuByConditionsResponseVo{

    @ApiModelProperty("页码")
    private Integer pageNo = 1;

    @ApiModelProperty("每页记录数")
    private Integer pageSize = 10;

    @ApiModelProperty("总记录数")
    private Long totalRecords = 0L;

    @ApiModelProperty("SKU清单")
    private List<SearchReplaceSkuDto> skuList;

    @ApiModelProperty("提示选配免费商品文案")
    private String freeMessage;
}
