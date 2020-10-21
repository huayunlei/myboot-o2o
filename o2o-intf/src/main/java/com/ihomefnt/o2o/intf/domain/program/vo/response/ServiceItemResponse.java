package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-09-04 16:49
 */
@Data
@ApiModel("服务费返回对象")
public class ServiceItemResponse {

    @ApiModelProperty("方案id")
    private Long solutionId;

    @ApiModelProperty("平面设计图url")
    private String solutionGraphicDesignUrl;

    @ApiModelProperty("户型图")
    private String apartmentUrl;

    @ApiModelProperty("拆改户型图")
    private String reformApartmentUrl;

    @ApiModelProperty("服务项目集合")
    private List<ServiceItemDto> serviceItemList;
}
