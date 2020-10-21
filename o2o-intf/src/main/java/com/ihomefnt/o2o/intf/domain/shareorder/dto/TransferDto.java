/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月23日
 * Description:TransferDto.java 
 */
package com.ihomefnt.o2o.intf.domain.shareorder.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author zhang
 */
@Data
public class TransferDto {

	// 主键
	@Id
	private String id;

	// 外键
	private String fk;

	// 业务类型 ( 0:shareorder,1:share_order_buildingtopic )
	private Integer type;

	// 删除标识(两张表都叫deleteFlag, 同步过来,出现为空的,赋值0)
	private Integer deleteFlag;

	/**
	 * 创建时间<br/>
	 * shareorder的 createTime是 long <br/>
	 * share_order_buildingtopic 的 createTime是 date <br/>
	 * 同步过来, 统一date<br/>
	 */
	private Date createTime;

	/**
	 * 创建人<br/>
	 * shareorder的 userId <br/>
	 * share_order_buildingtopic 的createUserId<br/>
	 * 同步过来
	 */
	private Integer createUserId;

	/**
	 * 是否置顶 <br/>
	 * shareorder的 没有赋值 0<br/>
	 * share_order_buildingtopic 的 topTag<br/>
	 * 同步过来<br/>
	 * 出现为空,赋值0<br/>
	 */
	private Integer topTag;

	/**
	 * 是否审核通过 <br/>
	 * shareorder的 shareOrderStatus<br/>
	 * share_order_buildingtopic 的status<br/>
	 * 同步过来<br/>
	 * 出现为空,赋值0<br/>
	 */
	private Integer status;

}
