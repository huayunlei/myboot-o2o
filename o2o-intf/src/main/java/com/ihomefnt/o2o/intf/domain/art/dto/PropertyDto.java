package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-07 14:38
 */
@Data
@ApiModel(value = "艺术定制品属性")
public class PropertyDto {

    @ApiModelProperty(value = "属性序号")
    private Integer propertySeq;

    @ApiModelProperty(value = "属性名称")
    private String propertyName;

    @ApiModelProperty(value = "属性值")
    private String propertyValue;

    @ApiModelProperty(value = "属性类型 1为颜色")
    private Integer propertyType;
}
