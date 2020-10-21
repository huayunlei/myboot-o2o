package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author hua
 * @Date 2019-07-09 15:13
 */
@ApiModel(description = "查询版本和热更新response")
@Data
public class VersionAndHotUpdateResponse {

    @ApiModelProperty(value = "热更新信息")
    private HotUpdateResponse hotUpdateResponse;
    @ApiModelProperty(value = "app版本信息")
    private AppVersionCheckResponseVo versionResponse;
}
