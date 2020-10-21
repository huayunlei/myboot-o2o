package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 
 * @author ZHAO
 */
@Data
@ApiModel("产品方案-空间商品信息参数")
public class SolutionExtraInfoRequest extends HttpBaseRequest{
	@ApiModelProperty("空间Id组合")
	private List<Integer> roomIds;
	
	@ApiModelProperty("方案ID集合")
	private List<Integer> programIds;
}
