/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年6月20日
 * Description:QueryMasterOrderIdByHouseIdResultDto.java
 */
package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 根据房产id查询唯一的有效的大订单号返回对象
 *
 * @author chong
 */
@Data
public class QueryMasterOrderIdByHouseIdResultDto {
    //房产id
    private Long houseId;

    // 大订单id
    private Integer masterOrderNum;

    //大订单状态
    private Integer masterOrderStatus;

    //订单来源
    private Integer source;

    //订单子状态
    private Integer orderSubStatus;

}
