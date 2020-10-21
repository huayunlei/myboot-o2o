package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2019-07-09 15:09
 */
@ApiModel(description = "查询版本和热更新请求参数")
@Data
@Accessors(chain = true)
public class QueryVersionAndHotUpdateRequest extends HttpBaseRequest {

    @ApiModelProperty(value = "app版本号")
    private Integer appVersionCode;

    @ApiModelProperty(value = "模块列表")
    private List<ReqModule> modules;

    @ApiModelProperty("应用唯一码")
    private String appId;

}
