/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;

/**
 * @author zhang 消息通知模板实体类
 */
@Data
public class PushTempletEntityDto {

	private Integer templetId; // 主键

	private String templetTitle;// 模板标题

	private String templetContent;// 模板内容

	// 消息类型:13艾积分到账,14硬装施工报告,15艺术品订单发货,16艾商城通知
	private Integer noticeType;

	// 消息类型:13艾积分到账,14硬装施工报告,15艺术品订单发货,16艾商城通知
	private Integer noticeSubType;

	private Integer noticeWay;// 提醒方式： 1 短信,2APP推送(消息盒子)

	private Integer noticeStatus;// 通知状态:1生效0失效

	private String toUrl;// 跳转后URL

	private String photoUrl;// 图片url

	private Integer unReadCount; // 每次未读消息数增加数量

	private Integer saveInMsgCenter;// 是否进消息盒子 :1是0否

	private Integer messageGroupStatus;// 是否需要消息分组 :1是0否

}
