/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.experiencestore.vo.response;

import java.util.List;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpHouseSuitProductResponse;
import com.ihomefnt.o2o.intf.domain.house.dto.House;

/**
 * @author Administrator
 *
 */
@Data
public class HttpExperienceStoreDetailResponse {
    private Long esId;//体验店ID
    private List<String> images;//体验店头图
    @JsonIgnore
    private String image;//体验店头图
    private String owner;//艾佳体验店
    private String communityName;//小区名称;
    @JsonIgnore
    private String workDate;
    private String businessHours; //营业时间
    @JsonIgnore
    private String distance;//距离
    private Double latitude;
    private Double longitude;
    private String experienceAddress; //体验店地址
    private String hQPhone;//总部电话
    private String shopManagerPhone;//店长电话
    @JsonIgnore
    private Long buildingId;//楼盘id
    private List<HttpHouseSuitProductResponse> houseSuitList; 
    
    private List<House> houseList;//户型列表
    private String headImg;//头图
    private String name;
    
    private int suitStatus; //套装状态 1.上线 0.下线
    private Integer isOfflineExp; //1.为线下体验店 0.线上体验店
    private boolean hasExpShop;//是否有线下体验店
    private String communityDesc;//体验店描述
    private int houseCount;//户型个数
    private int modelCount;//实体样板间个数
    private int caseCount;//案例个数
    private String expDistance;//距离线下体验店的地址
    private String expName; //线上体验店的名称
    private String expAddress;  //线上体验店的地址
    private List<ActivityLabel> activityLabelList; //体验店近期活动
}
