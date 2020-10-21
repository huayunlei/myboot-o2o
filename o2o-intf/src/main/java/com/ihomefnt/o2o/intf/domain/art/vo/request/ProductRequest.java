package com.ihomefnt.o2o.intf.domain.art.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-07 10:57
 */
@ApiModel("新版艾商城产品请求数据")
@Data
public class ProductRequest extends HttpBaseRequest {

    @ApiModelProperty("产品id")
    private String productId;

}
