/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年6月20日
 * Description:VersionResponse.java
 */
package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author zhang
 */
@Data
@ApiModel("VersionResponse")
public class AppVersionResponseVo {

    @ApiModelProperty("主键id")
    private Long vId;

    @ApiModelProperty("appId")
    private String appId;

    @ApiModelProperty("app 名称")
    private String appName;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("下载地址")
    private String download;

    @ApiModelProperty("强制更新标志  0不更新   1更新")
    private Boolean must;

    @ApiModelProperty("更新描述")
    private String updateContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("渠道编号")
    private String partnerValue;

    @ApiModelProperty("App名称")
    private String appTypeName;

    @ApiModelProperty("最近强更版本号")
    private String mustUpdateVersion;

    @ApiModelProperty("APP类型：1安卓、2iOS")
    private Integer appType;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty(" 0 未上架 1已上架")
    private Integer putAwayState;

    @ApiModelProperty("基带版本")
    private Integer baseAppVersion;

    @ApiModelProperty("app icon")
    private String icon;

    @ApiModelProperty("包大小")
    private Double size;

    @ApiModelProperty("是否强更：1:是 0:否")
    private Integer updateFlag;
}
