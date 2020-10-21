/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:BundleVo.java 
 */
package com.ihomefnt.o2o.intf.domain.bundle.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
public class BundleCheckParamDto extends HttpBaseRequest {

    @ApiModelProperty(value = "bundle版本号")
    private String bundleVersion;
}
