package com.ihomefnt.o2o.intf.domain.homepage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wanyunxin
 * @create 2019-03-26 17:58
 */
@ApiModel("BOM下单参数")
@Accessors(chain = true)
@Data
public class BomReplaceRequest {

    @ApiModelProperty("原商品SkuId")
    private Integer groupId;

    @ApiModelProperty("新商品SkuId")
    private Integer newGroupId;

    @ApiModelProperty("家具类型：2 赠品家具, 4 bom 组合")
    private Integer furnitureType;

    @ApiModelProperty("柜体标签编号")
    private String cabinetType;

    @ApiModelProperty("柜体标签名称")
    private String cabinetTypeName;

    @ApiModelProperty("位置索引")
    private String positionIndex;
}
