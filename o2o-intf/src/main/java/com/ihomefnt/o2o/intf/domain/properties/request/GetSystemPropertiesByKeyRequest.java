package com.ihomefnt.o2o.intf.domain.properties.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-07-09 18:11
 */
@Data
@ApiModel("获取系统配置请求")
public class GetSystemPropertiesByKeyRequest extends HttpBaseRequest {

    private String propertiesKey;
}
