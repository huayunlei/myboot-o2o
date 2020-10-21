package com.ihomefnt.o2o.intf.domain.homepage.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */
@Data
@ApiModel("首页框架请求参数")
public class HomeFrameRequest extends HttpBaseRequest {

	@ApiModelProperty("订单号")
	private Integer orderId;
}
