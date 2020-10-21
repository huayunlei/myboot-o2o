package com.ihomefnt.o2o.intf.domain.product.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

import java.util.List;

/**
 * Created by wzhang on 15-1-19.
 */
@Data
public class HttpProductMoreSingleRequest150 extends HttpBaseRequest{
	
    private Boolean isNavigation;//是否需要导航
    private List<Long> filterIdList;//过滤条件
    private int pageSize;

    private int pageNo;
    
    private Long nodeId;
}
