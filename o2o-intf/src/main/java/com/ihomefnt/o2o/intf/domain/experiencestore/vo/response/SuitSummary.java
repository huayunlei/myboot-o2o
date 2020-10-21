package com.ihomefnt.o2o.intf.domain.experiencestore.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SuitSummary implements Serializable {

    private static final long serialVersionUID = -5916378534292792395L;

    private Long suitId;    //套装数量
    private String suitName;    //套装名称
    private Integer offlineExperience = 0;  //是否可以线下体验  1 是   0 否
    private String panorama3dUrl; //3D全景地址
}
