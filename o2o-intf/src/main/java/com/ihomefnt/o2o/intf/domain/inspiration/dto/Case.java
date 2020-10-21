package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import lombok.Data;

import java.util.List;
@Data
public class Case {
    private Long caseId;    //主键
    private String caseName;  //案例名
    private String designerName;  //设计师名
    private String styleName;    //风格
    private String size;      //面积
    private String houseName;   //户型
    private String feature;     //说明
    private String images;        //图片
    private List<String> imageList;        //图片
    private String description;   //图文说明
    private String desc;   //图文h5
    private String createTime;   //创建时间
    private String status;      //状态

	private int readCount;      //阅读次数
	private int transpondCount;  //转发次数
	private String designerUrl;  //设计师头图
}
