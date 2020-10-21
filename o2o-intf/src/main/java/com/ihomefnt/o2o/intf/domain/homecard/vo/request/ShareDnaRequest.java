package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * DNA分享
 * @author ZHAO
 */
@Data
public class ShareDnaRequest extends HttpBaseRequest{
	private Long shareId;

}
