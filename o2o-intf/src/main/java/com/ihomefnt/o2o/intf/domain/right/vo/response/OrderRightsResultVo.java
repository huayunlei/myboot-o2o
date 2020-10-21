package com.ihomefnt.o2o.intf.domain.right.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * OrderRightsResultVo link to OrderRightsResultDto
 * @author jerfan cang
 * @date 2018/10/11 10:49
 */
@ApiModel("OrderRightsResultVo")
@Data
public class OrderRightsResultVo {

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
    List<GradeNodeVo> nodeDtoList ; //(array[GradeNodeDto], optional): 等级节点,

    @ApiModelProperty("权益分类列表")
    List<OrderClassifyInfoVo> classifyInfoList ; //(array[OrderClassifyInfo], optional): 权益分类列表,

    @ApiModelProperty("普通等级已消费记录，-app不用此字段")
    List<RigthtsItemDetailVo> commonRightsUsedList ; //(array[RigthtsItemDetail], optional): 普通等级已消费记录
    
    @ApiModelProperty("普通以上等级已消费或者消费中记录")
    List<RightsItemConsumeVo> rightsItemConsumeDtoList;// 普通以上等级已消费或者消费中记录

    @ApiModelProperty("升级项金额")
    private BigDecimal upItemAmount;

    @ApiModelProperty("艾佳贷权益项-从权益分类中单独抽出来")
    private RigthtsItemDetailVo loan;

    @ApiModelProperty("现金益项-从权益分类中单独抽出来")
    private RigthtsItemDetailVo cash;

    @ApiModelProperty("权益项个数-根据底层节点计算")
    private Integer itemCount;

    @ApiModelProperty("服务器时间-app使用")
    private long currentTime;

    @ApiModelProperty("券面值")
    private BigDecimal couponAmount;

    @ApiModelProperty("原合同额")
    private BigDecimal originalOrderAmount;

    @ApiModelProperty("可抵扣金额")
    private BigDecimal deductionAmount;

    @ApiModelProperty("已确认收款金额")
    private BigDecimal confirmReapAmount;

    @ApiModelProperty("权益版本号 1老版本 2新版本")
    private Integer versionFlag = 2;

    @ApiModelProperty("是否可升级 true可升级 false不可升级")
    private boolean upgradable = false;

    @ApiModelProperty("可升级权益")
    List<GradeNodeVo> upgradableList ;

    private Boolean isCustomRightVersion = Boolean.FALSE;

}
