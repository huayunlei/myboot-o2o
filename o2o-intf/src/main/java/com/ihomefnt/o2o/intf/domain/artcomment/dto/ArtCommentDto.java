/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月9日
 * Description:ArtCommentDto.java 
 */
package com.ihomefnt.o2o.intf.domain.artcomment.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class ArtCommentDto {

	/**
	 * mongoId
	 */
	@Id
	private String commentId;

	/**
	 * 用户打分
	 */
	private Integer userStar;

	/**
	 * 发表内容
	 */
	private String userComment;

	/**
	 * 订单Id
	 */
	private Integer orderId;

	/**
	 * 商品Id
	 */
	private Integer productId;

	/**
	 * 是否发表到晒家:true是 false否
	 */
	private Boolean toShareOrderTag;

	/**
	 * 发表图片
	 */
	private List<String> images;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 手机号码
	 */
	private String mobileNum;

	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 用户头像
	 */
	private String nickImg;


	/**
	 * 订单类型: 1 软装类型 2 硬装类型 3 全品家类型 5 艺术品类型 6文旅商品 15小星星艺术类型
	 */
	private Integer orderType;

	/**
	 * 商品类型: 5 艺术品类型 6文旅商品
	 */
	private Integer productType;

	/**
	 * 创建时间
	 */
	private String createTime;
	
	private Integer deleteFlag;//删除标志 （-1已删除 0未删除）
	
	private String deleteTime;//删除时间
	
	private Integer replyUserId;//回复用户ID
	
	private String replyNickName;//回复用户昵称
	
	private String pid;//父节点
	
	private Integer replyNum;//回复数量

	private String productName;//艺术品名称
	
	private String orderNum;//订单号

}
