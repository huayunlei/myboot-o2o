package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liyonggang
 * @create 2019-03-19 14:27
 */
@Data
@ApiModel("组合保存返回数据")
public class GroupSaveVO {

    @ApiModelProperty("新组合id")
    private Integer groupId;

    @ApiModelProperty("新组合名称")
    private String groupName;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String groupImage;

    @ApiModelProperty("差价")
    private BigDecimal priceDifferences;

    @ApiModelProperty("图片集合")
    private List<ImageVO> images;
}
