package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 贷款信息
 * @author ZHAO
 */
@Data
public class AladdinLoanInfoResponseVo {
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 大订单编号
	 */
	private Long orderNum;
	
	/**
	 * 订单类型
	 */
	private Integer orderType;
	
	/**
	 * 贷款状态 
	 */
	private Integer loanStatus;

	/**
	 * 贷款状态 描述
	 */
	private String loanStatusStr;
	
	/**
	 * 贷款金额
	 */
	private BigDecimal amount;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 操作人
	 */
	private Long operator;
	
	/**
	 * 删除标识 0：未删除 1：已删除
	 */
	private Integer delFlag;

}
