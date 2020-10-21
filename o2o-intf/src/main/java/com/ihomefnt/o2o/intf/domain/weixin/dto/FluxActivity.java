/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.weixin.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author zhang
 * 微信领取流量活动
 */
@Data
public class FluxActivity {

	private Long activityId;// 活动id

	private String name;// 活动名称

	private Timestamp beginTime;// 活动开始时间

	private Timestamp endTime;// 活动结束时间

	private String description;// 活动描述
}
