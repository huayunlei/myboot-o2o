package com.ihomefnt.o2o.intf.domain.staticdata.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-11-21 19:02
 */
@Data
@ApiModel("产品服务数据")
public class ProductServiceDataResponse {

    @ApiModelProperty("顶部图片")
    private SimpleImageResponse topImage;

    @ApiModelProperty("底部图片")
    private SimpleImageResponse bottomImage;

    @ApiModelProperty("资源图片地址")
    private List<StaticResourceDto> resourceList;

}
