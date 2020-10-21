package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
@Data
public class Suit {
    private Long suitId;
    private String suitName;
    private String imagesUrl;
    private String typeName;//新品特卖、低价爆款、大牌推荐
    private Integer typeId;
    
    private int offLine;
    private String url3d;
    private String suitImg;//套装头图
    private int productCount;//多少件套
    private double suitPrice;//套装价格
    private List<String> suitLabel;//套装标签（"样板间"和"3D"，没有标签传null）
    private boolean has3d;//是否有3d图
    private boolean hasModel;//是否有样板间
    
    @JsonIgnore(value = true)
    private String images;
    
    private String styleName;   //风格名称
    private String feature; //简述
}
