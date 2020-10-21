package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 组合替换简单信息
 *
 * @author liyonggang
 * @create 2019-03-21 10:07
 */
@Data
@ApiModel("组合替换简单信息")
public class QueryGroupReplaceDetailSimpleVO {


    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("默认组合信息")
    private GroupSimpleInfo defaultGroup;

    @ApiModelProperty("替换组合详情")
    private GroupSimpleInfo replaceGroup;

    @Data
    @ApiModel("组合信息")
    public static class GroupSimpleInfo {

        @ApiModelProperty("组合id")
        private Integer groupId;

        @ApiModelProperty("组合名称")
        private String groupName;

        @ApiModelProperty(value = "组合图片",hidden = true)
        @JsonIgnore
        private String groupImage;

        @ApiModelProperty("组合图片集合")
        private List<ImageVO> groupImageList;

        @ApiModelProperty("差价")
        private BigDecimal priceDifferences;
    }
}
