package com.ihomefnt.o2o.intf.domain.main.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2019/3/22
 */

@ApiModel("简要交付信息-APP首页使用")
@Data
public class DeliverySimpleInfoDto {

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("交付单号")
    private Integer deliverId;

    @ApiModelProperty("是否需要确认需求标示，可需求确认为true")
    private Boolean confirmFlag;

    @ApiModelProperty("软装是否在采购中")
    private Boolean purchaseFlag;

    @ApiModelProperty("是否出质保标示,目前已完结状态代表出质保期为true")
    private Boolean outWarrantyFlag;

    @ApiModelProperty("交付状态：0待交付 1需求确认 2交付准备 3施工中/采购中 5安装 6竣工 7质保 8完结")
    private Integer deliverStatus;

    @ApiModelProperty("交付状态：0待交付 1需求确认 2交付准备 3施工中/采购中 5安装 6竣工 7质保 8完结")
    private String statusName;

    @ApiModelProperty("软装交付状态：-1待交付 0待采购 1采购中 2代送货 3送货中 4送货完成 7已取消")
    private String softStatusName;

    @ApiModelProperty("硬装施工进度")
    private String projectStatusName;

    @ApiModelProperty("硬装施工进度 0准备开工 1开工交底 2水电阶段 3自施工阶段 4瓦木阶段 5竣工阶段 6施工完成")
    private Integer projectStatus;

    @ApiModelProperty("工期")
    private Integer constructionPeriod;

    @ApiModelProperty("已开工天数")
    private Integer startedDays;

    @ApiModelProperty("需求确认时间")
    private String confirmDateStr;

    @ApiModelProperty("预计进场开工日期")
    private String planBeginDate;

    @ApiModelProperty("预计完工时间")
    private String planEndDate;

    @ApiModelProperty(value = "客户最终验收状态 0-未验收；1-不通过；2-已通过")
    private Integer ownerCheckStatus;

    @ApiModelProperty(value = "艾师傅最终验收状态 0-未验收；1-不通过；2-已通过")
    private Integer masterCheckStatus;

    @ApiModelProperty("已开工天数单位")
    private String startedDaysUnit;

    @ApiModelProperty("艾师傅是否已上传全品家验收单")
    private Boolean fastCheckApproval;

    @ApiModelProperty("艾管家手机号")
    private String managerMobile;
}
