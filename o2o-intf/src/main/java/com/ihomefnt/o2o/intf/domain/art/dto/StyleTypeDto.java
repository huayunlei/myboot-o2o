package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-07 09:53
 */
@ApiModel("艺术风格对象")
@Data
public class StyleTypeDto {

    @ApiModelProperty("风格名称")
    private String styleId;

    @ApiModelProperty("风格名称")
    private String styleName;
}
