package com.ihomefnt.o2o.intf.domain.maintain.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询报修详情
 * @author ZHAO
 */
@Data
@ApiModel("查询报修详情请求参数")
public class MaintainTaskDetailRequestVo extends HttpBaseRequest{

	@ApiModelProperty("报修任务ID")
	private Integer taskId;
}
