package com.ihomefnt.o2o.intf.domain.loan.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 * @date 2018/6/21
 */
@ApiModel("爱家贷-创建爱家贷参数")
@Data
@Accessors(chain = true)
public class CreateLoanRequestVo extends HttpBaseRequest{
    @ApiModelProperty("订单编号 必传")
    private Integer orderNum;

    @ApiModelProperty("贷款金额 必传")
    private BigDecimal amount;

    @ApiModelProperty("贷款年限 必传")
    private Integer loanYears;

    @ApiModelProperty("贷款银行 必传")
    private Integer bankId;

    @ApiModelProperty("贷款申请人姓名")
    private String applyer;

    @ApiModelProperty("贷款申请人身份证号")
    private String applyerIdNum;

    @ApiModelProperty(value = "是否校验申请人和身份证号 0-否 1-是",hidden = true)
    private Integer checkApplyer = 1;

    @ApiModelProperty(value = "贷款方式 0：期次一次性 1：分期分摊 必传,",hidden = true)
    private Integer loanType = 1;
}
