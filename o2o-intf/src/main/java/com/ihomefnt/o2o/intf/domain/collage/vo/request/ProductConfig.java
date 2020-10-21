package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/17 11:20
 */
@Data
@ApiModel("ProductConfig")
public class ProductConfig {

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品数量")
    private Integer amount;

    @ApiModelProperty("商品备注")
    private String remark;
}
