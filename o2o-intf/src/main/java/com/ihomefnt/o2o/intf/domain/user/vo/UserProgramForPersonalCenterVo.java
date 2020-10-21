package com.ihomefnt.o2o.intf.domain.user.vo;

import com.ihomefnt.o2o.intf.domain.program.vo.response.HouseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 个人中心我的方案响应数据
 *
 * @author liyonggang
 * @create 2019-02-20 14:28
 */
@ApiModel("个人中心我的方案详情")
@Data
public class UserProgramForPersonalCenterVo {

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("方案头图")
    private String pic;

    @ApiModelProperty("权益等级")
    private Integer gradeId;

    @ApiModelProperty("等级名称")
    private String gradeName;//等级名称

    @ApiModelProperty("是否是老订单(0. 老订单 1. 新订单)")
    private Integer oldOrder;

    @ApiModelProperty("置家顾问电话")
    private String adviserPhone;

    @ApiModelProperty("用户是否已预确认方案，0:未预确认  1：已确认")
    private Integer preConfirmed;//用户是否已预确认方案，0:未预确认  1：已确认

    @ApiModelProperty("艾升级券面值")
    private BigDecimal upGradeCouponAmount;// 艾升级券面值

    @ApiModelProperty("已选方案风格名称")
    private String solutionStyleName;//已选方案风格名称

    @ApiModelProperty("已选风格id")
    private Integer solutionStyleId;//已选方案风格id

    @ApiModelProperty("是否有提交过的方案(已经提交下单的方案) true:有,false:没有")
    private Boolean hasSubmitProgram;

    @ApiModelProperty("是否有预选的方案(包括未提交的和已提交的) true:有,false:没有")
    private Boolean hasPrimaryProgram;

    @ApiModelProperty("是否交过定金")
    private Boolean hasPayed;

    @ApiModelProperty("订单状态 1：接触阶段，2：意向阶段，3：定金阶段，4：签约阶段，5：交付中，6：已完成，7：已取消")
    private Integer orderStatus;

    @ApiModelProperty("房产信息")
    private HouseResponse houseInfo;

    @ApiModelProperty("方案价格")
    private BigDecimal totalPrice;

}
