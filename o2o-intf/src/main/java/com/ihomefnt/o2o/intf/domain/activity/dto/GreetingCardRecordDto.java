package com.ihomefnt.o2o.intf.domain.activity.dto;

import lombok.Data;

import java.util.Date;

/**
 * 1219定制贺卡记录
 * @author ZHAO
 */
@Data
public class GreetingCardRecordDto {
	private Integer id;//主键ID
	
	private Integer pid;//父节点
	
	private Integer picId;//原图图片ID
	
	private String mobile;//用户手机号
	
	private String imageUrl;//制作的图片URL
	
	private String blessingWords;//祝福语
	
	private String signature;//署名
	
	private String shareResult;//分享结果
	
	private Integer source;//参与渠道：0、其他 1、APP 2、H5
	
	private Integer winningResult;//中奖结果：0 为中奖  1中奖
	
	private Integer recordType;//记录类型：0制作卡片  1保存图片到本地  2微信转发  3分享朋友圈
	
	private Date createTime;//创建时间
	
	private Date updateTime;//更新时间
	
	private Date deleteTime;//删除时间
	
	private Integer deleteFlag;//删除标志：0未删除 1已删除
}
