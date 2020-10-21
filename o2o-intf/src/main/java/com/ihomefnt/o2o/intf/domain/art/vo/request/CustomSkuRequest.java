package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-07 12:53
 */
@Data
@ApiModel(value = "定制商品查询入参")
public class CustomSkuRequest extends HttpBaseRequest {

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "作品编号")
    private String worksId;
}
