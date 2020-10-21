package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-11 14:08
 */
@Data
@ApiModel("属性分类信息")
public class PropertyGroupDto {

    @ApiModelProperty(value = "属性名称")
    private String propertyName;

    @ApiModelProperty("属性列表")
    private List<PropertyDto> propertyList;

}
