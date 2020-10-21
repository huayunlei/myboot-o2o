package com.ihomefnt.o2o.intf.domain.visusal.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-24 下午6:28
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
@ApiModel("判断是否支持sku可视化选配入参")
public class VisualFurnitureMatchingRequest extends HttpBaseRequest {

    @ApiModelProperty("方案id")
    private Integer programId;

    @ApiModelProperty("空间id")
    private Integer spaceId;

}
