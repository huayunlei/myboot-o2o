package com.ihomefnt.o2o.intf.domain.cart.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@ApiModel("艾积分返回数据")
public class HttpAjbInfoResponseVo {
	
	 /**
     *艾积分子账户id
     */
	@ApiModelProperty("艾积分账户id")
    private Integer ajbAccountId;

    /**
     *账户ID
     */
	@ApiModelProperty("总账户ID")
    private Integer accountId;

    /**
     *会员id
     */
	@ApiModelProperty("会员id")
    private Integer userId;
	
	@ApiModelProperty("艾积分可用总数量")
    private Integer amount;
	
	 /**
     *总额
     */
	@ApiModelProperty("艾积分可用总金额")
    private BigDecimal amountMoney;

    /**
     * 艾积分汇率
     */
    @ApiModelProperty("艾积分汇率")
	private Integer exRate;
	
	 /**
     * 冻结金额
     */
	@ApiModelProperty("冻结数量")
    private Integer frozenAmount;

    /**
     *状态
     */
	@ApiModelProperty("状态")
    private Short status;

    /**
     *创建时间
     */
	@ApiModelProperty("创建时间")
    private Date createTime;
}
