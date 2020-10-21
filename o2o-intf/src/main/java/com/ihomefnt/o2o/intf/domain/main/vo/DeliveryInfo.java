package com.ihomefnt.o2o.intf.domain.main.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/20
 */

@ApiModel("交付信息")
@Data
@Accessors(chain = true)
public class DeliveryInfo {

    @ApiModelProperty("工期")
    private Integer constructionPeriod;

    @ApiModelProperty("已开工天数")
    private Integer startedDays;

    @ApiModelProperty("硬装进度")
    private String hardProgress;

    @ApiModelProperty("软装进度")
    private String softProgress;

    @ApiModelProperty("软装数量")
    private Integer softTotal;

    @ApiModelProperty("软装完成数")
    private Integer softFinishNum;

    @ApiModelProperty("预计进场开工日期")
    private String planBeginDate;

    @ApiModelProperty("预计完工时间")
    private String planEndDate;

    @ApiModelProperty(value = "客户最终验收状态 0-未验收；1-不通过；2-已通过，默认未验收")
    private Integer ownerCheckStatus = 0;

    @ApiModelProperty(value = "是否竣工")
    private Boolean deliveryEnd;

    @ApiModelProperty("交付状态：0待交付 1需求确认 2交付准备 3施工中/采购中 5安装 6竣工 7质保 8完结")
    private Integer deliverStatus;

    @ApiModelProperty(value = "艾师傅最终验收状态 0-未验收；1-不通过；2-已通过")
    private Integer masterCheckStatus;

    @ApiModelProperty("已开工天数单位")
    private String startedDaysUnit;

    @ApiModelProperty("艾管家姓名")
    private String surveyorName;

    @ApiModelProperty("艾管家手机号")
    private String surveyorPhone;
}
