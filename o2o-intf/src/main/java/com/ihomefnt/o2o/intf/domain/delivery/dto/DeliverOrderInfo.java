package com.ihomefnt.o2o.intf.domain.delivery.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author liyonggang
 * @create 2019-08-19 11:02
 */
@Data
public class DeliverOrderInfo {

    @ApiModelProperty("交付单号")
    private Integer deliverId;

    @ApiModelProperty("订单编号")
    private Integer orderId;

    @ApiModelProperty("交付状态：0待交付 1 需求确认 2交付准备 3施工中/采购中 5安装 6竣工 7质保 8完结")
    private Integer status;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    @ApiModelProperty("交付完成按钮显示 true显示")
    private Boolean display;

    @ApiModelProperty("确认拿钥匙按钮显示 true显示")
    private Boolean confirmKey;

    @ApiModelProperty("交付状态")
    private String statusName;

    @ApiModelProperty("艾管家id")
    private Integer managerId;

    @ApiModelProperty("艾管家名称")
    private String managerName;

    @ApiModelProperty("艾管家手机号")
    private String mobile;

    @ApiModelProperty("方案id")
    private Integer solutionId;

    @ApiModelProperty("交付负责人id")
    private Integer principalId;

    @ApiModelProperty("交付负责人名称")
    private String principalName;

    @ApiModelProperty("交房日期")
    private Date houseDeliverDate;

    @ApiModelProperty("计划开工日期")
    private Date planBeginDate;

    @ApiModelProperty("实际开工日期")
    private Date actualBeginDate;

    @ApiModelProperty("实际竣工日期")
    private Date actualEndDate;

    @ApiModelProperty("预计交付日期")
    private Date planEndDate;

    @ApiModelProperty("交付时长 天")
    private Integer deliverDay;

    @ApiModelProperty("软装进度")
    private String softProcess;

    @ApiModelProperty("硬装进度")
    private String hardProcess;

    @ApiModelProperty("软装数量")
    private Integer softTotal;

    @ApiModelProperty("软装完成数")
    private Integer softFinishNum;

    @ApiModelProperty("工程施工状态")
    private String projectStatus;

    @ApiModelProperty("计划拿钥匙")
    private Date planTakeKeyDate;

    @ApiModelProperty("实际拿钥匙")
    private Date actualTakeKeyDate;
}
