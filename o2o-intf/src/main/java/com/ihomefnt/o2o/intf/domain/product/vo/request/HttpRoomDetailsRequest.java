package com.ihomefnt.o2o.intf.domain.product.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by wangxiao on 15-12-14.
 */
@Data
public class HttpRoomDetailsRequest extends HttpBaseRequest {

	private Long roomId;

}
