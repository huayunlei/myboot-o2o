package com.ihomefnt.o2o.intf.domain.lechange.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改设备名称
 * @author ZHAO
 */
@Data
@ApiModel("修改设备名称请求参数")
public class HttpModifyDeviceNameRequest extends HttpBaseRequest{

	@ApiModelProperty("授权Token")
	private String token;

	@ApiModelProperty("设备序列号")
	private String deviceId;

	@ApiModelProperty("通道号")
	private String channelId;

	@ApiModelProperty("设备名称")
	private String name;

}
