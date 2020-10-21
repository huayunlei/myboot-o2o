package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 查询我的订单权益
 * 等级节点
 * @author jerfan cang
 * @date 2018/10/11 9:28
 */
@Data
@ApiModel("GradeNodeDto")
public class GradeNodeDto {

    @ApiModelProperty("等级id")
    private Integer gradeId ; //(integer, optional): 等级,

    @ApiModelProperty("等级名称")
    private String gradeName ; //(string, optional): 等级名称,

    @ApiModelProperty("获取当前等级时间")
    private String getGradeTimeStr ; // (string, optional): 获取当前等级时间,

    @ApiModelProperty("是否是当前节点")
    private boolean currentNode ; //(boolean, optional): 是否是当前节点,

    @ApiModelProperty("当前等级需要的额度")
    private BigDecimal gradeAmount ; //(number, optional): 当前等级需要的额度,

    @ApiModelProperty("已收金额")
    private BigDecimal fundAmount ; //(number, optional): 已收金额,

    @ApiModelProperty("获取当前节点所需金额")
    private BigDecimal getCurrentGradeAmount ; //(number, optional): 获取当前节点所需金额,

    @ApiModelProperty("获得当前等级截止时间")
    private String getCurrentNodeTimeStr ; //(string, optional): 获得当前等级截止时间
}
