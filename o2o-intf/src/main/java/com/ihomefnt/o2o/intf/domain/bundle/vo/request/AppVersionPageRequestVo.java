package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author hua
 * @Date 2019-12-11 14:45
 */
@Data
@ApiModel("app version 分页查询请求参数")
public class AppVersionPageRequestVo extends BasePageRequest {

    @ApiModelProperty("应用唯一码")
    private String appId;
}
