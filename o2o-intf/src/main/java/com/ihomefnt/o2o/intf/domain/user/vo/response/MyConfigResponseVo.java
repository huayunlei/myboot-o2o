package com.ihomefnt.o2o.intf.domain.user.vo.response;

import com.ihomefnt.o2o.intf.domain.address.doo.TReceiveAddressDo;
import lombok.Data;

/**
 * Created by hvk687 on 10/16/15.
 */
@Data
public class MyConfigResponseVo {
	private TReceiveAddressDo receiveAddress;

	private Integer littleCofferShow;// 1表示显示，0表示不显示

	private Integer inviteUserShow;// 置业顾问:1表示显示，0表示不显示

	private String inviteUserUrl;// 置业顾问链接

	private Integer desinerShow;// 设计师首页按钮：1表示显示，0表示不显示

	private Integer screenShow;// 花瓶显示不显示入口：1表示显示，0表示不显示

	private String desinerHomeUrl;// 设计师首页链接

	private String dealDetailUrl = ""; // 抢购活动商品链接

	private String productProgress; // 项目进度 如：水电工程（已完成80%）

	private String hardOrderDetail; // 硬装订单详情url

	private String hardLivepic; // 硬装直播图片

	private String abountIhomeUrl;// 关于艾佳生活介绍URL

}
