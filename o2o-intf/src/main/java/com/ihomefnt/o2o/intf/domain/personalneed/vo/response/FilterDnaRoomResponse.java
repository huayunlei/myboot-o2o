package com.ihomefnt.o2o.intf.domain.personalneed.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.DnaRoomInfoResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("FilterDnaRoomResponse")
@Data
public class FilterDnaRoomResponse {

	@ApiModelProperty("空间标识ID")
	private Integer dnaMarkId;
	
	@ApiModelProperty("空间用途Id")
	private Integer dnaPurposeId;
	
	@ApiModelProperty("空间Id-默认用途使用")
	private Integer dnaRoomId;// dnaRoomId,
	
	@ApiModelProperty("空间类型-1厅 2室 3厨 4卫 5阳台 6储藏间 7衣帽间")
	private Integer roomClassifyType;
	
	@ApiModelProperty("空间类型下空间序号")
	private Integer sortNum;
	
	@ApiModelProperty("空间信息")
	private List<DnaRoomInfoResponse> roomInfoList;
}
