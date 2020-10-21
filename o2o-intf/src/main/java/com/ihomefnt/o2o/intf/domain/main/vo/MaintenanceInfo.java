package com.ihomefnt.o2o.intf.domain.main.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/20
 */

@ApiModel("维保信息")
@Data
@Accessors(chain = true)
public class MaintenanceInfo {

    @ApiModelProperty("报修记录次数")
    private Integer maintainCount;

    @ApiModelProperty("是否出质保标示,目前已完结状态代表出质保期为true")
    private Boolean outWarrantyFlag;

}
