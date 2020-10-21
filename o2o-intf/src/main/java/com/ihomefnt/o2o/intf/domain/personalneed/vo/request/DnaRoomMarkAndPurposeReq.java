package com.ihomefnt.o2o.intf.domain.personalneed.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("DnaRoomMarkAndPurposeReq")
public class DnaRoomMarkAndPurposeReq {

	@ApiModelProperty("空间标识ID")
	private Integer dnaMarkId;// dnaId
	
	@ApiModelProperty("空间用途Id")
	private Integer dnaPurposeId;
}
