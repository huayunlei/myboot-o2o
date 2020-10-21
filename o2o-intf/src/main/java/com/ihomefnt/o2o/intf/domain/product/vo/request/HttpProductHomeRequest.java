package com.ihomefnt.o2o.intf.domain.product.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-17.
 */
@Data
public class HttpProductHomeRequest extends HttpBaseRequest{
	private Double latitude; //纬度
    private Double longitude; //经度
}
