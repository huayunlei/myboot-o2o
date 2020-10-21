package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/1/2
 */
@ApiModel(description = "上报日志请求")
@Data
@Accessors(chain = true)
public class UploadLogRequest extends AppBaseRequest {

    @ApiModelProperty(value = "网络状况：WIFI,MOBILE")
    private String network;

    @ApiModelProperty(value = "用户信息")
    private String userInfo;

    @ApiModelProperty(value = "hot_update->热更新日志，crash->崩溃日志")
    private String type;

    @ApiModelProperty(value = "本地Bundle版本号")
    private Integer nativeBundleVersionCode;

    @ApiModelProperty(value = "远程bundle版本号")
    private Integer remoteBundleVersionCode;

    @ApiModelProperty("更新方式：1-全量，2增量")
    private Integer updateFlag;

    @ApiModelProperty("结果码")
    private Integer resultCode;

    @ApiModelProperty("更新结果说明")
    private String resultMsg;

    @ApiModelProperty(value = "堆栈日志")
    private String stackLog;

    @ApiModelProperty(value = "模块唯一码")
    private String moduleCode;

}
