package com.ihomefnt.o2o.intf.domain.product.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

import java.util.List;
@Data
public class HttpSuitRequest150 extends HttpBaseRequest{

    private Boolean isNavigation;//是否需要导航

    private List<Long> filterIdList;//过滤条件

    private int pageSize;

    private int pageNo;
    
    private String roomName;
    
    private Integer roomType; //0.客厅     1.餐厅     2.卧室    3.书房
}
