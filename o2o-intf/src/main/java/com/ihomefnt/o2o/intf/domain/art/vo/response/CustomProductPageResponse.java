package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.CustomProductDto;
import com.ihomefnt.o2o.intf.domain.art.dto.StyleTypeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author wanyunxin
 * @create 2019-08-07 11:23
 */
@ApiModel("可定制商品图片列表")
@Data
public class CustomProductPageResponse {

    @ApiModelProperty("可选定制商品列表")
    private List<CustomProductDto> list;

    List<StyleTypeDto> styleTypeDtos;

    @ApiModelProperty("总记录数")
    private Integer totalSize;
}
