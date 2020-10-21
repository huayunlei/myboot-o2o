/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.experiencestore.vo.response;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * @author Administrator
 *
 */
@Data
public class HttpExperienceStoreResponse {
    private Long esId;//体验店ID
    private List<String> images;//体验店头图
    @JsonIgnore(value = true)
    private String image;//体验店头图
    private String communityName;//小区名称;
    private String businessHours; //营业时间
    private String distance;//距离
    private String expAddress;//体验店地址
    private int houseCount;//样板间个数
    private int suitSale;//人气
    private int suitCount;//套装数量
    private String expLabel;//体验店标签（只显示一个，"距离最近"(优先级最高)，"套装最多"，"人气最旺"，没有标签传null）
    
    
    private List<HouseSummary> houseSummaryList;
    private boolean hasExpShop;//是否有线下体验店
    private Double expWeight = 0.0;  //体验店权重值
    private int houseTypeCount; //户型个数
    private int caseCount;  //方案个数
    private Integer dist;
    private String expStoreImage;//体验馆头图
    private List<ActivityLabel> activityLabelList;  //体验店活动列表
}
