package com.ihomefnt.o2o.intf.domain.product.vo.response;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by wzhang on 15-1-19.
 */
@Data
public class HttpProductNodeSize extends HttpBaseRequest{
	

    private Long pageNo;
    
    private Long nodeId;

    private String sortType;
}
