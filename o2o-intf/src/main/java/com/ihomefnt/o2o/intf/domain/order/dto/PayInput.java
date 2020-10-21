package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付输入
 * 
 * @author Ivan
 * @date 2016年6月8日 上午10:17:06
 */
@Data
public class PayInput implements Serializable{
	
	private static final long serialVersionUID = 4506439920308563343L;

	/**
	 * 订单编号
	 */
	@NotBlank(message = "订单编号不能为空")
	private String orderNum;
	
	/**
	 * 本次支付金额
	 */
	@DecimalMin(value="0.01",message = "支付金额不正确")
	private double money;

	/**
	 * 支付来源
	 */
	private int source;
	
	/**
	 * 商品展示地址
	 * 
	 * 需以http://开头的完整路径
	 * 例如：http://www.ihomefnt.com/spacedetail/727
	 */
	private String showUrl;
	
	/**
	 * 支付完成跳转页面
	 */
	private String returnUrl;
	
	/**
	 * 支付完成后异步通知页面路径
	 */
	private String notifyUrl;
	
	/**
	 * 用户IP
	 */
	private String ip;
	
	
	private Integer userId;
	
	private Integer orderType;

	/**
	 * 实际支付金额
	 *
	 */
	private BigDecimal actualPayMent;

	/**
	 * 本次总支付金额
	 *
	 */
	private BigDecimal totalPayMent;

	/**
	 * 支付阶段
	 */
	private Integer payStage;

	/*
	 * 操作系统 1android 2ios
	 */
	private Integer platform;

}
