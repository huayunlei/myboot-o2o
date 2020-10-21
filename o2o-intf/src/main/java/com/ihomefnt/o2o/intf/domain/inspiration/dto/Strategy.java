package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import lombok.Data;

import java.util.List;
@Data
public class Strategy {
	private Long strategyId;    //主键
    private String strategyName;  //攻略名
    private String feature;      //攻略说明
    private String images;       //图片
    private List<String> imageList;       //图片
    private String description;  //图文
    private String desc;  //图文url
    private String createTime;  //创建时间
    private String status;      //状态
    
	private int readCount;      //阅读次数
	private int transpondCount;  //转发次数
	
}
