package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 户型信息
 * @author ZHAO
 */
@Data
public class CustomerHouseLayoutInfo {
    private Integer houseId;//户型id

    private String houseName;//户型名称

    private BigDecimal area;//面积

    private Integer hall;//厅

    private Integer chamber;//卧室

    private Integer kitchen;//卫

    private Integer balcony;//厨房

    private Integer toilet;//阳台


}
