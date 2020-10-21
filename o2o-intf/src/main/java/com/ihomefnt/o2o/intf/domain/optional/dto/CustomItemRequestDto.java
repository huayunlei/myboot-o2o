package com.ihomefnt.o2o.intf.domain.optional.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2018-11-24 16:41
 */
@Data
@ApiModel("订制品组件")
public class CustomItemRequestDto {

    @ApiModelProperty("组件id")
    private Integer id;

    private List<TreeNodesRequestDto> attrs;
}
