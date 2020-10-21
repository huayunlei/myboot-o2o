package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/19 20:46
 */
@Data
@ApiModel("CollageOrderDetailResponseVo")
public class CollageOrderDetailResponseVo {

    @ApiModelProperty("CollageOrderBaseInfoVo")
    private CollageOrderBaseInfoVo orderInfo;

    @ApiModelProperty("productInfo 商品信息")
    private ProductVo productInfo;

    @ApiModelProperty("CollageLogistVo")
    private CollageLogistVo logistyInfo;
}
