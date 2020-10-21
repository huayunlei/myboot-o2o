package com.ihomefnt.o2o.intf.domain.suit.dto;

import lombok.Data;

@Data
public class TWpfStyleImage {

    private Integer wpfStyleImageId;    //图片ID
    private String image;   //图片地址
    private String description; //描述
    private Integer displayOrder;   //显示顺序
    
    private String imageDetail;//详细信息的图片地址
    
}
