package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("专题详情请求参数")
public class SubjectDetailRequest extends HttpBaseRequest{
	@ApiModelProperty("专题ID")
	private Integer subjectId;
}
