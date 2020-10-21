package com.ihomefnt.o2o.intf.domain.product.vo.response;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigDecimal;
@Data
public class SuitList {
    
    private String houseName;//户型名称几室几厅几卫
    private double size;//面积
    private String avatar;//设计师头像
    private int sales;//销量
    private Long suitId;//套装id
    private String suitName;//套装名称
    private String suitFImages;//套装头图
    private String styleName;//套装风格
    private Double price;//套装价格
    private Integer suitProductCount;//商品件数
    
    private Integer status;
    
    @JsonIgnore(value = true)
    private String images;
    
    @JsonIgnore(value = true)
    private String discount;//折扣

    public String getHouseName() {
    	String str = BigDecimal.valueOf(size).stripTrailingZeros().toPlainString();
    	return houseName + " | " + str + "平方 | " + String.valueOf(suitProductCount) + "件套 | " + styleName;
    }
    
}
