package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * 楼盘公司信息
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
public class UsableCompanyResponseVo {
    private Integer companyId;//公司ID

    private String companyName;//公司名称

    private List<UsableBuildingVo> buildingList;//楼盘列表
}
