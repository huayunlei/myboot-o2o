package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付明細列表
 *
 * @author liyonggang
 * @create 2018-11-05 18:25
 */
@Data
@ApiModel("支付明細列表")
public class TransactionListVo implements Serializable {
    private static final long serialVersionUID = -2728673331321829504L;
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("交易方式 取值：1-现金；2-微信；3-支付宝；4-POS机；5-网划；6-建业通宝,")
    private Integer payType;
    @ApiModelProperty("交易方式字符串")
    private String payTypeStr;
    @ApiModelProperty("交易时间")
    private String createTimeStr;
    @ApiModelProperty("款项类型，取值：1-诚意金，2-定金，3-合同额,")
    private String paymentType;
    @ApiModelProperty("款项类型字符串")
    private String paymentTypeStr;
    @ApiModelProperty("交易金额")
    private BigDecimal transactionAmount;
    @ApiModelProperty("状态")
    private Integer amountStatus;
    @ApiModelProperty("支付结果")
    private String payResult;
    @ApiModelProperty("交易类型，取值：1-收款，2-退款")
    private Integer transactionType;
    @ApiModelProperty("交易类型名称,")
    private String transactionTypeStr;

    private String groupTyme;

    public String getGroupTyme() {
        try {
            return DateFormatUtils.format(DateUtils.parseDate(createTimeStr, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
