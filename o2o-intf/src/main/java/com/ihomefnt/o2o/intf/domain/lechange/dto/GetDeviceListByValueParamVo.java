package com.ihomefnt.o2o.intf.domain.lechange.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jiangjun
 */

@Data
@Accessors(chain = true)
public class GetDeviceListByValueParamVo {

    private String value;//搜索值

    private Integer status;//设备状态 0:绑定 3 空闲, 为空 :表示查询全部

    private Integer brand = 1; // 0 不限制  1 乐橙  2 魔看
}
