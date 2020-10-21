package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="ProductFilterInfoRequest",description="产品版块筛选条件请求参数")
public class ProductFilterInfoRequest extends HttpBaseRequest {

	@ApiModelProperty("是否空间用途全部数据 0否 1是 默认0")
	private Integer isRoomUsedAll = 0;
}
