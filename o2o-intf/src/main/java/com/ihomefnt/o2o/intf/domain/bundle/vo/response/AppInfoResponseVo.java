package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @description:
 * @author: 何佳文
 * @date: 2019-09-12 13:58
 */
@Data
public class AppInfoResponseVo {

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "应用Id")
    private String appId;

    @ApiModelProperty(value = "应用Id")
    private String icon;

    @ApiModelProperty(value = "应用秘钥")
    private String appSecret;

    @ApiModelProperty(value = "应用类型：1-IOS,2-Android")
    private Integer appType;

    @ApiModelProperty(value = "类型：1:Native,2-H5")
    private Integer type;

    @ApiModelProperty(value = "ios:bundleId,android:包名")
    private String bundleId;

    @ApiModelProperty(value = "最新版本")
    private String appVersion;

    @ApiModelProperty(value = "是否上架：0:未上架 1:已上架")
    private Integer state;

    @ApiModelProperty(value = "应用描述")
    private String description;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("下载地址")
    private String download;

}
