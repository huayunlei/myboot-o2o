package com.ihomefnt.o2o.intf.domain.maintain.dto;

import lombok.Data;

import java.util.List;

/**
 * 报修任务创建更新
 * @author ZHAO
 */
@Data
public class TaskCreateUpdateDto {

	private String linkmanMobile; // 联系人手机号

	private String linkmanName;// 联系人姓名

	private Integer orderId;// 订单id

	private String taskDesc;// 问题描述

	private List<String> pictureUrls;// 维修图片

	private Integer id;//报修单号

}