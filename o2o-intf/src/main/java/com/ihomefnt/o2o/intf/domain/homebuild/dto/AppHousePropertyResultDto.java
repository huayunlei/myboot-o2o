package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinCustomerInfoVo;
import com.ihomefnt.o2o.intf.domain.user.dto.HousePropertyInfoResultDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hongjingchao on 2017/6/15.
 *"房产信息
 *
 * */
@Data
public class AppHousePropertyResultDto {
    @ApiModelProperty(value = "客户房产基本信息")
    private HousePropertyInfoResultDto housePropertyInfoResultDto;

    @ApiModelProperty(value = "客户房产扩展信息")
    private HousePropertyInfoExtResultDto housePropertyInfoExtResultDto;

    @ApiModelProperty(value = "客户信息")
    private AladdinCustomerInfoVo customerBaseResultDto;

}
