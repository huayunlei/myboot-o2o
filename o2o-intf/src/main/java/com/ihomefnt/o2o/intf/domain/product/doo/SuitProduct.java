package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SuitProduct {
	private Long suitId;
    private String images;
    private Integer offLineExperience;
    private Timestamp createTime;
    private String style;
    private String pictureUrlHouse;// 户型照片
    private String experienceAddress;//体验地址
    private Double latitude;//维度
    private Double longitude;//经度
}
