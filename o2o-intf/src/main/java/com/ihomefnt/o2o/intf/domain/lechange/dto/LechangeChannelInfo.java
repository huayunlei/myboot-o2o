package com.ihomefnt.o2o.intf.domain.lechange.dto;

import lombok.Data;

/**
 * 乐橙通道信息
 * @author ZHAO
 */
@Data
public class LechangeChannelInfo {
	private Integer channelId;//通道号 
	
    private String channelName;//通道名称 
    
    private boolean channelOnline;//是否在线 
    
    private String channelPicUrl;//缩略图URL
    
	private Integer alarmStatus;//报警布撤防状态，0-撤防，1-布防
	
	private Integer csStatus;//云存储状态：-1-未开通 0-已失效 1-使用中 2-套餐暂停
	
	private boolean shareStatus;//是否分享给别人的,true表示分享给了别人,false表示未分享给别人

	public LechangeChannelInfo() {
		this.channelId = 0;
		this.channelName = "";
		this.channelOnline = false;
		this.channelPicUrl = "";
		this.alarmStatus = 0;
		this.csStatus = -1;
		this.shareStatus = false;
	}

}
