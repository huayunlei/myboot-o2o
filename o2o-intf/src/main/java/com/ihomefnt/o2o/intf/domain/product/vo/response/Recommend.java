package com.ihomefnt.o2o.intf.domain.product.vo.response;

import lombok.Data;

import java.util.*;
@Data
public class Recommend {
    
    private Long recId;
    
    private String iconUrl;//icon图标url

    private String recName;// 名称

    private Long filterId;// filter id

    private Integer grid;// 代表1，代表2，代表3，代表4
    
    private Long nodeId;

    private List<Product> recProductList;
}
