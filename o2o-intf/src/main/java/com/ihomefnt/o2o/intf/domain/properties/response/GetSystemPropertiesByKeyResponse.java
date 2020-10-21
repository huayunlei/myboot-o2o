package com.ihomefnt.o2o.intf.domain.properties.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取系统配置数据
 *
 * @author liyonggang
 * @create 2019-07-09 18:12
 */
@Data
@Accessors(chain = true)
public class GetSystemPropertiesByKeyResponse {

    private String propertiesKey;

    private String propertiesValue;
}
