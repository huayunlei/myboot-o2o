package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 用户的客户信息
 * @author ZHAO
 */
@Data
public class CustomerDetailInfo {
    private Integer customerId;//客户编号

    private String name;//姓名

    private String mobile;//手机号
    
    private Integer buildingId;//楼盘ID
    
    private String companyCode;//公司id 7艾佳总部,8南京公司,9河南公司,10北京公司

    private CustomerAdviserInfo adviser;//置家顾问信息

    private CustomerHouseAddressInfo houseAddress;//房屋位置信息

    private CustomerHouseLayoutInfo houseLayout;//户型信息

    private BigDecimal payMoney;//已收金额

    private String payTime;//收款时间

}
