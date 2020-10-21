package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class JpushParamDto{

	private Long pushId; // 推送ID

	/**
	 * 通知大类：
	 * 1订单类通知,2咨询回复类通知,3卡券到账通知,4整体套装推送,5经典案例推送,
	 * 6攻略推送,7营销活动类推送 8灵感文章
	 */
	private Long noticeType;

	/**
	 * 通知小类：
	 * 1用户订单配送,2用户订单签收,3用户订单退款,4咨询回复,5现金券到账通知 ,
	 * 6套装推送,7案例主动通知,8攻略主动通知, 9营销活动 ,10灵感文章
	 */
	private Long noticeSubType;

	private String alias;// 用户别名,长度限制为40字节,默认用户电话号码

	private String tags;// 用户组tag标签,每个tag命名长度限制为40字节,最多支持设置100个tag,以逗号分隔，但总长度不得超过1K

	private String newsTitle;// 消息标题

	private String content;// 消息内容

	private String toUrl;// 消息链接跳转url

	private String photoUrl;// 图片url

	private String newsDescript;// 消息描述

	private Long noticeSrc;// 通知来源 ：1boss系统

	private Timestamp triggerTime;// 通知触发时间

	private Timestamp sendTime;// //通知发送时间
	
	private String sendTimeStr;// //通知发送时间字符串("2016-07-30 12:30:25")

	private Long noticeOrMessage;// 通知or消息:1通知0消息2未知

	private Long joinBoxStatus;// 是否进消息盒子 :1是0否2未知

	private Long messageGroupStatus;// 是否是消息组 :1是0否2未知
	
	private Long sendTimeType;//发送时间类型1：立即，2：定时
	
    private Long businessKey; //业务主键
    
    private Integer unReadCount; // 每次未读消息数增加数量
    
    private String platform; //推送平台

}