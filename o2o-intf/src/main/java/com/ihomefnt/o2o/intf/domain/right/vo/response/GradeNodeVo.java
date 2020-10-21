package com.ihomefnt.o2o.intf.domain.right.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jerfan cang
 * @date 2018/10/11 14:19
 */
@ApiModel("GradeNodeVo")
@Data
public class GradeNodeVo {


    @ApiModelProperty("等级")
    private Integer gradeId;

    @ApiModelProperty("等级名称")
    private String gradeName;

    @ApiModelProperty("等级图标")
    private String gradeLevelIcoUrl;

    @ApiModelProperty("获取当前等级时间")
    private String getGradeTimeStr;

    @ApiModelProperty("是否是当前节点")
    private boolean currentNode;

    @ApiModelProperty("当前等级需要的额度")
    private BigDecimal gradeAmount;

    @ApiModelProperty("已收金额")
    private BigDecimal fundAmount;

    @ApiModelProperty("获取当前节点所需金额")
    private BigDecimal getCurrentGradeAmount;

    @ApiModelProperty("获得当前等级截止时间")
    private String getCurrentNodeTimeStr;

}
