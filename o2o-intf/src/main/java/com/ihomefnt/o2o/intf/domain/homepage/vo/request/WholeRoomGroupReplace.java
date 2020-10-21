package com.ihomefnt.o2o.intf.domain.homepage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/8 13:06
 */
@Data
@ApiModel("全屋空间商品组替换列表")
public class WholeRoomGroupReplace {

    @ApiModelProperty("原商品组id")
    private Integer oldProdGroupId;

    @ApiModelProperty("新商品组id")
    private Integer newProdGroupId;
}
