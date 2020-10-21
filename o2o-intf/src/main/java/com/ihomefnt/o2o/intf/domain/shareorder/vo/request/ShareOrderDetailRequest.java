package com.ihomefnt.o2o.intf.domain.shareorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "晒家详情请求参数")
public class ShareOrderDetailRequest extends HttpBaseRequest {
	@ApiModelProperty("晒家id")
	private String shareOrderId;

	@ApiModelProperty("0:表示老晒家 1:表示楼盘运营")
	private int type;// 0:表示老晒家 1:表示楼盘运营
}
