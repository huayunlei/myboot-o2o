package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 
 * @author ZHAO
 */
@Data
@ApiModel("产品方案-空间商品调整信息参数")
public class ReplaceProductCreateRequest {
	@ApiModelProperty("空间id")
	private Integer roomId;
	
	@ApiModelProperty("替换商品信息")
	private List<ReplaceProductRequest> productDtos;

	@ApiModelProperty("选配图片")
	private List<String> visualImgs;

	public List<String> getVisualImgs() {
		return visualImgs;
	}
}
