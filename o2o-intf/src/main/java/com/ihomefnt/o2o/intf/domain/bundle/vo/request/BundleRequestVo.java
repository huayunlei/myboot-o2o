/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:BundleRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@ApiModel("bundle版本请求参数")
@Data
public class BundleRequestVo extends HttpBaseRequest {

    @ApiModelProperty(value = "bundle版本号")
    private String bundleVersion;
}
