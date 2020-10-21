package com.ihomefnt.o2o.intf.domain.personalneed.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.junit.experimental.theories.DataPoints;
@Data
@ApiModel("QueryDnaRoomRequest")
public class QueryDnaRoomRequest {
	@ApiModelProperty("空间用途Id")
	private Integer dnaPurposeId;
	
	@ApiModelProperty("每页数目")
	private int pageSize;

	@ApiModelProperty("当前第几页")
	private int pageNo;
}
