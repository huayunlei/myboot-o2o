package com.ihomefnt.o2o.intf.domain.lechange.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改设备连接热点
 * @author ZHAO
 */
@Data
@ApiModel("修改设备连接热点请求参数")
public class HttpModifyDeviceWifiRequest extends HttpBaseRequest{

	@ApiModelProperty("授权Token")
	private String token;

	@ApiModelProperty("设备序列号")
	private String deviceId;

	@ApiModelProperty("需要连接的SSID")
	private String ssid;

	@ApiModelProperty("BSSID")
	private String bssid;

	@ApiModelProperty("wifi密码")
	private String password;

}
