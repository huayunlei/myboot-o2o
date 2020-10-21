package com.ihomefnt.o2o.intf.domain.experiencestore.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class ExpStoreInfo implements Serializable{

	private static final long serialVersionUID = -6569703720581267166L;
	private Long expStoreId;
    private String expStoreName;//体验馆名称
    private String expStoreImage;//体验馆头图
    private String businessHours;//营业时间
    private String workDate;
    private String hqPhone;//总部电话
    private String smPhone;//店长电话
    private String location;//体验馆地址
    private String latitude;//纬度
    private String longitude;//经度
    private String images;//体验馆图片
    
    private String provinceName;
    private Integer suitNum;
    private Integer productNum;
    private Integer suitStyleNum;
    private Double suitMinPrice;
    private Long cityId;
    private String areaName;
    
}
