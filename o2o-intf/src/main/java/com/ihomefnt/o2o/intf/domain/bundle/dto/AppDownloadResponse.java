/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.bundle.dto;

import lombok.Data;

/**
 * @author weitichao
 *
 */
@Data
public class AppDownloadResponse {
	
	private String appDownloadUrl;  //app应用下载跳转url
	
	private String iosurl;  //iso应用下载地址
	
    private String androidurl;  //android应用下载地址
    
    private Integer type;  //返回值类型：1.表示微信下载页面 2.表示错误页面  3.表示成功页面
}
