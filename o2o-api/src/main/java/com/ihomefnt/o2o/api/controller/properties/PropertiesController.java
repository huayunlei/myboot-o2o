package com.ihomefnt.o2o.api.controller.properties;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.properties.request.GetSystemPropertiesByKeyRequest;
import com.ihomefnt.o2o.intf.domain.properties.response.GetSystemPropertiesByKeyResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author liyonggang
 * @create 2019-07-09 18:07
 */
@ApiIgnore
@Api(tags = "【业务环境变量获取API】",hidden = true)
@RestController
@RequestMapping("/properties")
public class PropertiesController {

    @Autowired
    private Environment env;

    @PostMapping("/getPropertiesByKey")
    @ApiOperation(value = "根据系统业务环境变量key获取系统配置", notes = "根据系统业务环境变量key获取系统配置")
    public HttpBaseResponse<GetSystemPropertiesByKeyResponse> getPropertiesByKey(@RequestBody GetSystemPropertiesByKeyRequest request) {
        String propertyValue = null;
        if (request.getPropertiesKey().startsWith("business.properties")) {
            propertyValue = env.getProperty(request.getPropertiesKey());
        }
        return HttpBaseResponse.success(new GetSystemPropertiesByKeyResponse().setPropertiesKey(request.getPropertiesKey()).setPropertiesValue(propertyValue));
    }
}
