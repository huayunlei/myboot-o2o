/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月23日
 * Description:ArtistResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhang
 */
@Data
public class ArtistResponseVo {

	private Integer id;// dna设计师在dna库中id,
	private Integer userId;// 用户id（对应用户中心主键）,
	private String designerName;// 设计师姓名,
	private String designerImage;// 设计师头像,
	private String designerMobile;// 设计师手机号,
	private BigDecimal totalCommission;// 设计师总的佣金,
	private String designerTag;// 设计师标签,
	private String designerProfile;// 设计师个人描述,
	private Integer status;// 设计师审核状态:1.待审核 2.审核通过 3.审核驳回,
	private String statusName;// 审核状态对应的名称,
	private String checkDesc;// 审核意见,
	private Integer updateUserId;// 更新人id(cms userId),
	private Integer reviewUserId;// 审核人id(cms userId),
	private String reviewerName;// 审核人姓名(cms userId),
	private Date createTime;// 创建时间,
	private String createTimeStr;// 创建时间字符,
	private Date updateTime;// 创建时间,
	private String updateTimeStr;// 更新时间字符
}
