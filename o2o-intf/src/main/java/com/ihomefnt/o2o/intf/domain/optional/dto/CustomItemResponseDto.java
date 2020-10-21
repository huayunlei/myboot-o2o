package com.ihomefnt.o2o.intf.domain.optional.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 新定制品定制组件
 * 订制项
 *
 * @author liyonggang
 * @create 2018-11-24 15:01
 */
@Data
@ApiModel("订制项")
public class CustomItemResponseDto {
	
    @ApiModelProperty("id")
    private Integer id;//(integer, optional): id,
    @ApiModelProperty("定制项名称")
    private String name;//(string, optional): 定制项名称,
    @ApiModelProperty("长")
    private Integer length;//(integer, optional): 长,
    @ApiModelProperty("宽")
    private Integer width;//(integer, optional): 宽,
    @ApiModelProperty("高")
    private Integer height;//(integer, optional): 高,
    @ApiModelProperty("深最大值")
    private Integer maxLength;//(integer, optional): 深最大值,
    @ApiModelProperty("宽最大值")
    private Integer maxWidth;//(integer, optional): 宽最大值,
    @ApiModelProperty("高最大值")
    private Integer maxHeight;//(integer, optional): 高最大值,
    @ApiModelProperty("深最小值")
    private Integer minLength;//(integer, optional): 深最小值,
    @ApiModelProperty("宽最小值")
    private Integer minWidth;//(integer, optional): 宽最小值,
    @ApiModelProperty("高最小值")
    private Integer minHeight;//(integer, optional): 高最小值,

    private List<TreeNodeResponseDto> attrs;//(array[新定制品属性信息], optional): 属性组
}
