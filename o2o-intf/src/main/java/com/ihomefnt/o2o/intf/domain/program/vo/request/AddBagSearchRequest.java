package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("可选增配包信息查询请求参数")
public class AddBagSearchRequest extends HttpBaseRequest{
	@ApiModelProperty("方案ID集合")
	private List<Integer> programIdList;
}
