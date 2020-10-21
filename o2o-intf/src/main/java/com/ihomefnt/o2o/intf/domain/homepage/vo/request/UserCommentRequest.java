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
@ApiModel("首页关于我们请求参数")
public class UserCommentRequest extends HttpBaseRequest {

	@ApiModelProperty("每页多少条")
	private Integer pageSize;
	
	@ApiModelProperty("当前页")
    private Integer pageNo;
}
