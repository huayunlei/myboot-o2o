package com.ihomefnt.o2o.intf.domain.right.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ihomefnt.o2o.intf.domain.right.dto.RightClassifyDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("我的订单权益2020版beta返回数据")
public class OrderRightsVo {


    @ApiModelProperty("装修补贴金额总计")
    private BigDecimal totalAmount;

    @ApiModelProperty("已付款金额是否达到补贴标准")
    private Boolean achieveSubsidyStandard = false;

    @ApiModelProperty("装修补贴明细")
    List<DecorationSubsidyDetail> decorationSubsidyDetailList ;

    @Data
    public static class DecorationSubsidyDetail{

        @ApiModelProperty("主键")
        private Long id;

        @ApiModelProperty("装修补贴基数")
        private BigDecimal totalAmount ;

        @ApiModelProperty("补贴利率（年化）")
        private String subsidyRate;

        @ApiModelProperty("预计补贴金额")
        private BigDecimal subsidySettleAmount ;

        @ApiModelProperty("补贴结算日")
        @JsonFormat(pattern = "yyyy/MM/dd" , timezone="GMT+8")
        private Date subsidySettleDate ;

        @ApiModelProperty("补贴类型 0 正常补贴 1退款的补贴 2:装修补贴回收")
        private Integer subsidyType =0;

        @ApiModelProperty("补贴时长")
        private Integer subsidyDays;

        @ApiModelProperty("状态 0:未结算 1:待退款 2:已退款 3:已驳回 4：已取消")
        private Integer status;

        @ApiModelProperty("交款时间")
        private Date fundTime;

        @ApiModelProperty("退款时间")
        private Date refundTime;

        @ApiModelProperty("交易单据号")
        private Long billId;

        @ApiModelProperty("收款金额")
        private BigDecimal fundAmount;

        @ApiModelProperty("退款金额")
        private BigDecimal refundAmount;

    }
}
