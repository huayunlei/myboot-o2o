package com.ihomefnt.o2o.intf.domain.personalneed.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("FilterQueryDnaRoomResponse")
public class FilterQueryDnaRoomResponse {
	
	@ApiModelProperty("是否默认：0否  1是")
	private int isDefault;
	
	@ApiModelProperty("dna空间信息")
	private List<FilterDnaRoomResponse> list;

}
