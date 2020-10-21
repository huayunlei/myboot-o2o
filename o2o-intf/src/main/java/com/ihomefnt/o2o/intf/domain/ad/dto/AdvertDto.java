package com.ihomefnt.o2o.intf.domain.ad.dto;

import lombok.Data;

import java.util.Date;

/**
 * 启动页
 * @author ZHAO
 */
@Data
public class AdvertDto {
	private Integer id; //主键id:图片id

	private String image; //启动页图片URL

	private String buttonUrl; //启动页按钮URL
	
	private Integer osType; //设备类型 ：1.表示android
	
	private Integer status; //是否有效

	private Integer groupId; //组id
	
	private Date startTime; //启动页图片生效时间

	private Date endTime; //启动页图片结束时间

	private Date updateTime; //启动页图片修改时间
	
	private Date deleteTime; //启动页图片删除时间
	
	private Integer deleteFlag;//删除标志 0 未删除  1已删除
}
