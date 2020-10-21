package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

import java.util.List;

@Data
public class TWpfStyle {
    private Integer wpfStyleId; //全品家风格ID
    private Integer styleKey;   //全品家风格Key
    private String styleName;   //全品家风格名称
    private String styleFirstImage; //全品家风格头图

    private String wpfStyle;//全品家风格
    private String wpfStylename;//全品家名称

    private List<TWpfStyleImage> wpfStyleImageList;

}