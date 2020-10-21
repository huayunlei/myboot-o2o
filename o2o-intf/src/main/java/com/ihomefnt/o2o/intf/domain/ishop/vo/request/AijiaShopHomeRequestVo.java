package com.ihomefnt.o2o.intf.domain.ishop.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("艾商城首頁vo")
public class AijiaShopHomeRequestVo extends HttpBaseRequest {
	
	@ApiModelProperty("纬度")
	private Double latitude;
	@ApiModelProperty("经度")
	private Double longitude;
	@ApiModelProperty("一个页面显示多少条数据")
	private int pageSize;
	@ApiModelProperty("第几页")
	private int pageNo;
}
