package com.ihomefnt.o2o.intf.domain.personalneed.vo.request;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("筛选dna空间查询参数")
public class FilterDnaRoomReq extends HttpBaseRequest {
	@ApiModelProperty("dna ID")
	private Integer dnaId;// dnaId
	
	@ApiModelProperty("用户房产Id")
	private Integer houseId;
	
	@ApiModelProperty("已选空间标识和空间用途集合")
	private List<DnaRoomMarkAndPurposeReq> markAndPurposeList;
	
	@ApiModelProperty("每页数目")
	private int pageSize;

	@ApiModelProperty("当前第几页")
	private int pageNo;
	
}
