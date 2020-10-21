package com.ihomefnt.o2o.intf.domain.inspiration.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class HttpMoreInspirationRequest extends HttpBaseRequest{
	
    private Boolean isNavigation;//是否需要导航
    private List<Long> filterIdList;//过滤条件
    private int pageSize;
    private int pageNo;
    private Long nodeId;
    private int needPicWidt;
}
