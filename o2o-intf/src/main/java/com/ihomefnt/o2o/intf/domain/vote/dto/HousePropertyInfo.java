package com.ihomefnt.o2o.intf.domain.vote.dto;

import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-05-08 11:23
 */
@Data
public class HousePropertyInfo {


    private CustomerBaseResultDto customerBaseResultDto;
    private HousePropertyInfoExtResultDto housePropertyInfoExtResultDto;
    private HousePropertyInfoResultDto housePropertyInfoResultDto;

    @Data
    public static class CustomerBaseResultDto {
        private String accountName;
        private String addWechatDate;
        private String addWechatDateStr;
        private String bankCardNumber;
        private String branchBankName;
        private String branchInnerNo;
        private String city;
        private int customerId;
        private String gender;
        private String headBankName;
        private String identifyNum;
        private int isAddWechat;
        private int maxGradeId;
        private String maxGradeName;
        private String mobile;
        private String name;
        private String openingBank;
        private int orderStatus;
        private String province;
        private String spareMobile;
        private String spareName;
        private int userId;
    }

    @Data
    public static class HousePropertyInfoExtResultDto {
        private String adviserName;
        private int budget;
        private String budgetStr;
        private int customerOrigin;
        private String customerOriginStr;
        private int deliverStatus;
        private String deliverStatusStr;
        private String deliverTime;
        private String remark;
        private String style;
        private String styleStr;
        private String usePerson;
        private int useType;
        private String useTypeStr;
    }

    @Data
    public static class HousePropertyInfoResultDto {
        private int adviser;
        private String adviserMobile;
        private String adviserName;
        private int agentId;
        private String agentName;
        private String area;
        private int buildingId;
        private String buildingInfo;
        private String buildingName;
        private String buildingType;
        private int companyId;
        private String companyName;
        private String customerName;
        private String deliverTime;
        private int fidDistrict;
        private int houseId;
        private String housePropertyInfo;
        private String housingNum;
        private int layoutBalcony;
        private int layoutCloak;
        private int layoutId;
        private String layoutInfo;
        private int layoutKitchen;
        private int layoutLiving;
        private String layoutName;
        private int layoutRoom;
        private int layoutStorage;
        private int layoutToilet;
        private String partitionName;
        private String roomNum;
        private String shortHousePropertyInfo;
        private String shortLayoutInfo;
        private String unitNum;
        private int userId;
        private int zoneId;
    }
}
