/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年6月28日
 * Description:VersionBundleRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("RN的bundle版本参数")
public class VersionBundleRequestVo extends HttpBaseRequest{

    @ApiModelProperty(value = "bundle版本号")
    private String bundleVersion;
}
