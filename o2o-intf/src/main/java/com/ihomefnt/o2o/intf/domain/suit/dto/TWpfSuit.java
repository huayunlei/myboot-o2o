package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

import java.util.List;
@Data
public class TWpfSuit {
    private Integer wpfSuitId;  //全品家套装ID
    private String wpfSuitName; //全品家套装名称
    private Double wpfPriceSqm; //全品家每平米价格
    private String wpfFirstImage;   //全品家套装头图
    private Integer displayOrder;   //显示顺序
    private String itemsImage; //所有项目图
    private String wpfAppFirstImage;  //套装详情页头图
    private String wpfStyleName;  //套装的风格名称
    private String wpfStyle;  //风格名称
    
    private List<TWpfStyle> wpfStyleList; //风格列表
    private List<TWpfStyleImage> wpfSuitBomImageList; 
    private List<TWpfMaterial> materialList; //材料分类
    private List<TWpfSuitAd> suitAdList; //硬装列表
    
}
