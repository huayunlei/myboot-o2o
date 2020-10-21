package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-05-13 11:34
 */
@Data
@Accessors(chain = true)
public class AladdinUserHouseInfo {


    private CustomerBaseResultDto customerBaseResultDto;
    private HousePropertyInfoExtResultDto housePropertyInfoExtResultDto;
    private HousePropertyInfoResultDto housePropertyInfoResultDto;

    private Integer orderStatus;

    private Integer orderId;

    @Data
    public static class CustomerBaseResultDto {
        private String accountName;
        private String addWechatDate;
        private String addWechatDateStr;
        private String bankCardNumber;
        private String branchBankName;
        private String branchInnerNo;
        private String city;
        private Integer customerId;
        private String gender;
        private String headBankName;
        private String identifyNum;
        private Integer isAddWechat;
        private Integer maxGradeId;
        private String maxGradeName;
        private String mobile;
        private String name;
        private String openingBank;
        private Integer orderStatus;
        private String province;
        private String spareMobile;
        private String spareName;
        private Integer userId;
    }

    @Data
    public static class HousePropertyInfoExtResultDto {
        private String adviserName;
        private Integer budget;
        private String budgetStr;
        private Integer customerOrigin;
        private String customerOriginStr;
        private Integer deliverStatus;
        private String deliverStatusStr;
        private String deliverTime;
        private String remark;
        private String style;
        private String styleStr;
        private String usePerson;
        private Integer useType;
        private String useTypeStr;
    }

    @Data
    public static class HousePropertyInfoResultDto {
        private Integer adviser;
        private String adviserMobile;
        private String adviserName;
        private Integer agentId;
        private String agentName;
        private String area;
        private Integer buildingId;
        private String buildingInfo;
        private String buildingName;
        private String buildingType;
        private Integer companyId;
        private String companyName;
        private String customerName;
        private String deliverTime;
        private Integer fidDistrict;
        private Integer customerHouseId;
        private String housePropertyInfo;
        private String housingNum;
        private Integer layoutBalcony;
        private Integer layoutCloak;
        private Integer layoutId;
        private String layoutInfo;
        private Integer layoutKitchen;
        private Integer layoutLiving;
        private String layoutName;
        private Integer layoutRoom;
        private Integer layoutStorage;
        private Integer layoutToilet;
        private String partitionName;
        private String roomNum;
        private String shortHousePropertyInfo;
        private String shortLayoutInfo;
        private String unitNum;
        private Integer userId;
        private Integer zoneId;
    }
}
