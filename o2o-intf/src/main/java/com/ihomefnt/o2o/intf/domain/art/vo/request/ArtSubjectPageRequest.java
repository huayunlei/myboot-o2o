package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("艺术品专题分页请求参数")
public class ArtSubjectPageRequest extends HttpBaseRequest{

	@ApiModelProperty("当前第几页")
	private Integer pageNo = 1; //默认用户选定第一页
	
	@ApiModelProperty("每页显示条数")
	private Integer pageSize = 10; //默认每页显示10条
}
