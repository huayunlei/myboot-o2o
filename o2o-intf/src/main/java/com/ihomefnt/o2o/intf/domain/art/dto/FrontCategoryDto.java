package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-07 17:45
 */
@ApiModel(value = "新艾商城艺术品app端分类对象")
@Data
public class FrontCategoryDto {

    @ApiModelProperty(value = "分类信息")
    private List<FrontCategoryInfoDto> frontCategoryInfo;

    @ApiModelProperty(value = "总前台类目数")
    private Integer totalCount;

    @ApiModelProperty(value = "总页数")
    private Integer totalPage;

}
