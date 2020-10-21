/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: Ivan Shen
 * Date: 2016年8月3日
 * Description:RradeRecordDto.java 
 */
package com.ihomefnt.o2o.intf.domain.user.vo.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账本记录
 * @author Ivan Shen
 */
@Data
@ApiModel("账本记录")
public class AccountBookRecordResponseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5318222144540523017L;

	@ApiModelProperty("交易id")
    private Integer accountBookRecordId;

	@ApiModelProperty("账户id")
    private Integer accountId;

	@ApiModelProperty("会员id")
    private Integer userId;

	@ApiModelProperty("交易金额")
    private BigDecimal amount;
    
	@ApiModelProperty("变动方向")
    private Integer direction;

	@ApiModelProperty("类型")
    private Integer type;

	@ApiModelProperty("状态")
    private Integer status;

	@ApiModelProperty("备注")
    private String remark = "";

	@ApiModelProperty("创建时间")
    private Date createTime;
	
	private String createTimeStr;
	
	@ApiModelProperty("订单号")
    private String orderNum = "";

	@ApiModelProperty("活动代码")
    private Integer activityCode = 0;

	@ApiModelProperty("账本类型（现金券 1, 诚意金 2, 定金 3, 佣金 4, 提现 5,艾积分 6）")
    private Integer accountBookType;
}
