package com.ihomefnt.o2o.intf.domain.lechange.vo.response;

import lombok.Data;

/**
 * 设备详情
 * @author ZHAO
 */
@Data
public class DeviceDetailResponse {
	private String cameraSn; //  摄像头sn码
	
	private String networkCard; //  无线网卡
	
	private String routerSn; //  路由sn码
	
	private String status; //  设备状态 0:绑定 3 空闲
	
	private String wifiName; //  无线网名称
	
	private String wifiPassword; //  无线网密码
	
	private boolean linkEnable;//是否连接了wifi
	
	private String ssid;//当前连接的热点名称

	public DeviceDetailResponse() {
		this.cameraSn = "";
		this.networkCard = "";
		this.routerSn = "";
		this.status = "";
		this.wifiName = "";
		this.wifiPassword = "";
		this.linkEnable = false;
		this.ssid = "";
	}

}
