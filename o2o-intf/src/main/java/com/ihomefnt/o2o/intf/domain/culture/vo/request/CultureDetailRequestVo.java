package com.ihomefnt.o2o.intf.domain.culture.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel("商品详情请求vo")
public class CultureDetailRequestVo extends HttpBaseRequest {

	@ApiModelProperty("商品id")
	@NotNull(message="商品id不能为空")
	private Integer itemId;
}
