package com.ihomefnt.o2o.intf.domain.optional.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 创建新的sku入参
 *
 * @author liyonggang
 * @create 2018-11-24 16:37
 */
@Data
@ApiModel("创建新的sku入参")
public class CreateCustomSkuRequestDto extends HttpBaseRequest {
    private Integer skuId;// skuId,
    private List<CustomItemRequestDto> customItemList;//定制项集合,
    @ApiModelProperty(hidden = true)
    private Integer operatorId;//操作人 id
    @ApiModelProperty("sku数量")
    private Integer skuCount;
}
