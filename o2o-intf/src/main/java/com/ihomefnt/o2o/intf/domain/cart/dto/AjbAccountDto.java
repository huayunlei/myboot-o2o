package com.ihomefnt.o2o.intf.domain.cart.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel("艾积分账户信息")
public class AjbAccountDto {

    /**
     * 艾积分子账户id
     */
    @ApiModelProperty("艾积分账户id")
    private Integer ajbAccountId;

    /**
     * 账户ID
     */
    @ApiModelProperty("总账户ID")
    private Integer accountId;

    /**
     * 会员id
     */
    @ApiModelProperty("会员id")
    private Integer userId;

    /**
     * 可用艾积分数量
     */
    @ApiModelProperty("可用艾积分数量")
    private Integer amount;

    /**
     * 冻结金额
     */
    @ApiModelProperty("冻结金额")
    private Integer frozenAmount;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Short status;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    private Integer usedAmount;// 已使用艾积分数量
    
    private Integer totalAmount;// 艾积分总数
    
    private Integer expiredAmount;// 过期的数量
    
    private String endTime;// 艾积分过期时间
    
    private Integer exRate;// 艾积分汇率
    
    private String expiredDesc;//过期说明
}