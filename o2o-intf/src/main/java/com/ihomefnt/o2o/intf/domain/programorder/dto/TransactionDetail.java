package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liyonggang
 * @create 2018-11-05 18:26
 */
@Data
@ApiModel("支付明細")
public class TransactionDetail implements Serializable {
    private static final long serialVersionUID = -2186772082606658103L;
    //贷款详情
    private IoansVo loanAijiaInfo;//
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("交易方式")
    private Long payType;
    @ApiModelProperty("交易方式字符串")
    private String payTypeStr;
    @ApiModelProperty("交易时间")
    private String createTimeStr;
    @ApiModelProperty("交易金额")
    private BigDecimal transactionAmount;
    @ApiModelProperty("支付状态")
    private Integer amountStatus;
    @ApiModelProperty("订单编号")
    private String orderNum;
    @ApiModelProperty("交易类型 取值：1-收款，2-退款,")
    private Long transactionType;
    @ApiModelProperty("收款来源")
    private Long paySource;
    @ApiModelProperty("收款来源中文")
    private String paySourceStr;
    @ApiModelProperty("凭证编号 ")
    private String payNo;
    @ApiModelProperty("交易参考号")
    private String posNo;
    @ApiModelProperty("艾家收款公司")
    private String receiveSideStr;
    @ApiModelProperty("确认退款时间")
    private String confirmTimeStr;
    @ApiModelProperty("客户银行卡号")
    private String bankCardNumber;
    @ApiModelProperty("银行总行")
    private String headBankName;
    @ApiModelProperty("银行支行")
    private String branchBankName;
    @ApiModelProperty("款项类型，取值：1-诚意金，2-定金，3-合同额")
    private Integer paymentType;
    @ApiModelProperty("款项类型名称")
    private String paymentTypeStr;
    @ApiModelProperty("客戶姓名")
    private String customerName;
}
