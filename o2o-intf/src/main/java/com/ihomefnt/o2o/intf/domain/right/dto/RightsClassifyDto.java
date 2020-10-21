package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权益分类基本信息
 *
 * @author jerfan cang
 * @date 2018/9/30 10:58
 */
@Data
@ApiModel("权益分类基本信息")
public class RightsClassifyDto {

    @ApiModelProperty("等级id")
    private Integer gradeId;

    @ApiModelProperty("等级名称")
    private String gradeName;

    @ApiModelProperty("权益分类ID")
    private Integer classifyId;

    @ApiModelProperty("权益分类编号")
    private Integer classifyNo;

    @ApiModelProperty("权益分类名称")
    private String classifyName;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("配置额度")
    private Integer rightsConfigLimit;

    @ApiModelProperty("可选额度")
    private Integer rightsConfirmedLimit;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("特权名称APP显示名称")
    private String classifyNameCopywriting;

    @ApiModelProperty("可享受特权数量APP文案")
    private String rightConfirmedLimitCopywriting;

    @ApiModelProperty("特权icon")
    private String classifyPicUrl;
}
