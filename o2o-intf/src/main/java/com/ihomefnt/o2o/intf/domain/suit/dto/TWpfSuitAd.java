package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

@Data
public class TWpfSuitAd {
    private Integer wpfSuitAdId;    //全品家套装商品信息ID
    private String image;   //图片
    private String title;   //标题
    private String description; //描述
    private Integer block;  //所属区域块
    private Integer displayOrder;   //展示顺序
}
