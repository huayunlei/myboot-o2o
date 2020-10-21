package com.ihomefnt.o2o.intf.domain.visusal.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-25 下午2:42
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "可视化选配", description = "可视化选配")
public class IsSupportSkuResponseVo {

    @ApiModelProperty(value = "是否支持可视化选配")
    private boolean supportSKU;
}
