package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * WCM代理视频信息返回对象
 * @author ZHAO
 */
@Data
public class VideoResponseVo {
	private Integer id = 0;//视频ID
	
	private String title = "";//视频标题
	
	private String subTitle = "";//视频副标题
	
	private Integer type = 0;//视频类别
	
	private String link = "";//视频路径
	
	private String introduction = "";//视频文案
	
	private String frontImg = "";//视频首图
	
	private Integer sortBy = 0;//视频排序
	
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());//添加时间
	
	private Timestamp updateTime = new Timestamp(System.currentTimeMillis());//更新时间
}
