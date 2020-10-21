package com.ihomefnt.o2o.intf.domain.product.vo.response;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
@Data
public class Product {
    
    private Long productId;
    
    @JsonIgnore(value = true)
    private String productName;
    
    private String imagesUrl;
    
    @JsonIgnore(value = true)
    private String styleName;
    
    @JsonIgnore(value = true)
    private String images;
}
