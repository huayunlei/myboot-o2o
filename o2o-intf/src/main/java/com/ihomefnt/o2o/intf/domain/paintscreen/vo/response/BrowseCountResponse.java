package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/12/4 0004.
 */
@Data
@ApiModel("浏览次数")
public class BrowseCountResponse {

    @ApiModelProperty("浏览次数")
    private Integer count;

    @ApiModelProperty("ID")
    private Integer id;
}
