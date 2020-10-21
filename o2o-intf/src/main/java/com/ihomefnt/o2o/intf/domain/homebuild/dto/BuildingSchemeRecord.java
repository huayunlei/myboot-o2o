package com.ihomefnt.o2o.intf.domain.homebuild.dto;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-12 上午10:23
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
public class BuildingSchemeRecord {

    //全国小区数
    private Integer countryCount;

    //服务全国订单用户数
    private Integer countryOrderCount;

    //已选方案订单数
    private Integer orderedCount;

}
