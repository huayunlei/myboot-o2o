package com.ihomefnt.o2o.intf.domain.main.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */
@ApiModel("首页框架请求参数")
@Data
public class MainFrameRequest extends HttpBaseRequest {

	@ApiModelProperty("订单号")
	private Integer orderId;

	@ApiModelProperty("版本号标记 1不露出已下线方案 2露出已下线方案")
	private Integer version = 1;
	

}
