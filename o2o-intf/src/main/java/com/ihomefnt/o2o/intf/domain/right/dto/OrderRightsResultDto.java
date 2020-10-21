package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询我的订单权益
 * @author jerfan cang
 * @date 2018/10/11 9:21
 */
@ApiModel("OrderRightsResultDto ")
@Data
public class OrderRightsResultDto {
    @ApiModelProperty("订单编号")
    private Integer orderNum ;

    @ApiModelProperty("订单状态")
    private Integer orderStatus ;

    @ApiModelProperty("订单等级ID")
    private Integer gradeId ; //(integer, optional): 等级,

    @ApiModelProperty("订单等级名称")
    private String  gradeName ; //(string, optional): 等级名称,

    @ApiModelProperty("已收金额")
    private BigDecimal fundAmount ; //(number, optional): 已收金额,

    @ApiModelProperty("总可选项数")
    private Integer totalCanChoose ; //(integer, optional): 总可选项数,

    @ApiModelProperty("等级节点")
    List<GradeNodeDto> nodeDtoList ; //(array[GradeNodeDto], optional): 等级节点,

    @ApiModelProperty("权益分类列表")
    List<OrderClassifyInfo> classifyInfoList ; //(array[OrderClassifyInfo], optional): 权益分类列表,

    @ApiModelProperty("普通等级已消费记录-底层如果存在则有此字段有值返回")
    List<RigthtsItemDetail> commonRightsUsedList ; //(array[RigthtsItemDetail], optional): 普通等级已消费记录
    
    @ApiModelProperty("普通以上等级已消费或者消费中记录")
    List<RightsItemConsumeDto> rightsItemConsumeDtoList;// 普通以上等级已消费或者消费中记录

    @ApiModelProperty("升级项金额")
    private BigDecimal upItemAmount;

    @ApiModelProperty("券面值")
    private BigDecimal couponAmount;

    @ApiModelProperty("原合同额")
    private BigDecimal originalOrderAmount;

    @ApiModelProperty("确认收款金额")
    private BigDecimal confirmedAmount;

}
