package com.ihomefnt.o2o.intf.domain.promotion.dto;

import lombok.Data;

@Data
public class HouseOrderBriefInfoVo {
    private Integer orderId;
    private String houseInfo;
    private String stage;
    private Integer state;
    private String houseNum;
    private String buildingInfo;//房产信息
}
