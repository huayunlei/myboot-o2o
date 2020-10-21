package com.ihomefnt.o2o.intf.domain.product.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by piweiwen on 15-1-20.
 */
@Data
public class HttpProductMoreInformationRequest extends HttpBaseRequest {

	private Long productId;
}
