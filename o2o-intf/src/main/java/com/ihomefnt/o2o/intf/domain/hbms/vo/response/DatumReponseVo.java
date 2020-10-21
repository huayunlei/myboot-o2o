package com.ihomefnt.o2o.intf.domain.hbms.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author wangjin
 *
 */
@Data
@ApiModel(description = "资料")
public class DatumReponseVo {
	@ApiModelProperty(value = "id")
	private String id;
	
	@ApiModelProperty(value = "类目")
	private String kind;

	@ApiModelProperty(value = "类型：0-图片，1-文档")
	private String type;

	@ApiModelProperty(value = "url")
	private String url;
}
