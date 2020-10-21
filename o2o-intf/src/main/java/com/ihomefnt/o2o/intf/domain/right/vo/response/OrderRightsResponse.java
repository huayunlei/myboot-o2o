package com.ihomefnt.o2o.intf.domain.right.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ihomefnt.o2o.intf.domain.right.dto.RightClassifyDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@ApiModel("我的订单权益2020版")
public class OrderRightsResponse {

    @ApiModelProperty("已确认收款金额")//后改为已付款金额
    private BigDecimal confirmReapAmount;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("装修补贴金额总计")
    private BigDecimal totalAmount;

    @ApiModelProperty("已付款金额是否达到补贴标准")
    private Boolean achieveSubsidyStandard = false;

    @ApiModelProperty("前端显示标记 1未达标 2 正常 3已达标计算中")
    private Integer achieveSubsidyStandardFlag = 1;

    @ApiModelProperty("我的基本权益背景图")
    private String mineDecorationSubsidyBgImg;

    @ApiModelProperty("基本权益")
    private RightClassifyDetail jiBenQuanYi;

    @ApiModelProperty("是否交清全款 0否 1是 2超过应付金额")
    private Integer allMoney = 0;

    @ApiModelProperty("金额状态 true隐藏 false显示")
    private Boolean moneyHideStatus = false;

    @ApiModelProperty("装修补贴款项记录")
    private List<DecorationSubsidyFrontDto> DecorationSubsidyNormalFront;

    @ApiModelProperty("装修补贴已回收类款项记录")
    private List<DecorationSubsidyFrontDto> DecorationSubsidyRefundFront;

    @Data
    @Accessors(chain = true)
    public static class DecorationSubsidyFrontDto {

        @ApiModelProperty("状态 0:待结算 1:待发放 2:已发放 3:待发放 4：已取消")
        private Integer status = 4;

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
    }
}
