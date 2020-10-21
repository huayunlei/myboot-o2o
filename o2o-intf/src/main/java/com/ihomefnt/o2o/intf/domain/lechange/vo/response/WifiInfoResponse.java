package com.ihomefnt.o2o.intf.domain.lechange.vo.response;

import lombok.Data;

/**
 * Wifi信息
 * @author ZHAO
 */
@Data
public class WifiInfoResponse {
	private String ssid;// [String]SSID，热点ID
	
	private String bssid;// [String]BSS ID，通常是一个MAC地址。
	
	private Integer linkStatus;// [int]0未连接，1连接中，2已连接。
	
    private String auth;// [String]认证模式：OPEN，WEP，WPA/WPA2 PSK，WPA/WPA2
    
    private Integer intensity;// [int]强度，0最弱，5最强

	public WifiInfoResponse() {
		this.ssid = "";
		this.bssid = "";
		this.linkStatus = 0;
		this.auth = "";
		this.intensity = 0;
	}

}
