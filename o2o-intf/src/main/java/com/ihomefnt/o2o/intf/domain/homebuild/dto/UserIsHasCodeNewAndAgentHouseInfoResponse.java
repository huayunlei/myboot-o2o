package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.BuildingHouseInfoResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-01-15 15:52
 */
@Data
@ApiModel("用户新老区分+经纪人房产信息")
public class UserIsHasCodeNewAndAgentHouseInfoResponse {

    @ApiModelProperty("是否是有邀请码的新用户")
    private Boolean newUserHasCode = false;

    @ApiModelProperty("经纪人房产信息")
    private BuildingHouseInfoResponse agentHouseInfo;

}
