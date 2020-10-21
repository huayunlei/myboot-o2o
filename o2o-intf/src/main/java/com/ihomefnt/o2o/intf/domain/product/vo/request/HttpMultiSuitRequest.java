package com.ihomefnt.o2o.intf.domain.product.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class HttpMultiSuitRequest extends HttpBaseRequest {
	private Long houseId;
    private Integer isExper;
}
