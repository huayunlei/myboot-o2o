package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("小星星艺术家列表请求参数")
public class StarArtistListRequest extends HttpBaseRequest {

	@ApiModelProperty("分页大小")
	private int pageSize = 10; //分页大小
	
	@ApiModelProperty("第几个分页")
	private int pageNo = 1; //第几个分页
	
	private int userId;
}
