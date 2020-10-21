package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/20 14:57
 */
@Data
@ApiModel("ProductFilterRequest")
public class ProductFilterRequest {

    @ApiModelProperty("操作员昵称 需要和验签一样 才能通过 ")
    private String nickName;

    @ApiModelProperty("验签")
    private String sign;

    @ApiModelProperty("操作的skuId 列表 查询时候不传")
    private List<Integer> skuIds;
}
