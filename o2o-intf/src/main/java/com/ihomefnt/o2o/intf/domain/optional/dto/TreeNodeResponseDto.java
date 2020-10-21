package com.ihomefnt.o2o.intf.domain.optional.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 定制品属性树
 * 树节点
 */
@Data
@ApiModel("订制品属性信息")
public class TreeNodeResponseDto implements Serializable {

    private static final long serialVersionUID = -299156824277253766L;
    /**
     * 主键 id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 节点类型 [1: 组, 2: 选项, 3: 可选项, 4:可填项]
     */
    @ApiModelProperty(value = "节点类型 [1: 组, 2: 选项, 3: 可选项]")
    private Integer nodeType;

    /**
     * 属性名
     */
    @ApiModelProperty(value = "属性名")
    private String name;

    @ApiModelProperty(hidden = true)
    private String nodeTreeName;

    /**
     * 父节点 id
     */
    @ApiModelProperty(value = "parentId")
    private Long parentId;

    /**
     * 子节点勾选项 id
     */
    @ApiModelProperty(value = "子节点勾选项 id")
    private Long optionId;

    /**
     * 是否被选中
     */
    @ApiModelProperty(value = "本节点是否被选中")
    private Boolean selected;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private List<TreeNodeResponseDto> attrs = new ArrayList<>();

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;
    /**
     * 必选项节点
     */
    @ApiModelProperty(value = "必选项节点")
    private Boolean requiredNode;
    /**
     * 最末选项根节点
     */
    @ApiModelProperty(value = "最末选项根节点")
    private Boolean lastSelectNode;
    /**
     * app 是否可定制
     */
    @ApiModelProperty("app 是否可定制")
    private Boolean appCustomizable;

    private Boolean titleNode;
}
