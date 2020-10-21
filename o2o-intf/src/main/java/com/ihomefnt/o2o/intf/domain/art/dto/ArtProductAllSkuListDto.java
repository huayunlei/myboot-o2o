package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-08 10:23
 */
@Data
@ApiModel(value = "定制商品oms接口返回数据")
public class ArtProductAllSkuListDto {

    @ApiModelProperty(value = "商品信息")
   private List<ArtSkuDto> artProductSkuODtoList;

    @ApiModelProperty(value = "库存")
    private Integer inventoryCount;

    @ApiModelProperty("上下架标记 0上架 1下架")
    private Integer onlineStatus;
}
