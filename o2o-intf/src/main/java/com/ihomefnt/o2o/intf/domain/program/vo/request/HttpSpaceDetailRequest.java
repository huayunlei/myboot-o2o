package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询空间详情请求参数
 * @author ZHAO
 */
@Data
@ApiModel("空间详情查询请求参数")
public class HttpSpaceDetailRequest extends HttpBaseRequest{
	@ApiModelProperty("方案ID")
	private Integer programId;
	
	@ApiModelProperty("空间ID")
	private Integer spaceId;
}
