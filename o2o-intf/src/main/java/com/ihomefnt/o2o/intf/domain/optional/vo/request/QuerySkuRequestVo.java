package com.ihomefnt.o2o.intf.domain.optional.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2018-11-24 15:16
 */
@Data
@ApiModel("sku查询")
public class QuerySkuRequestVo extends HttpBaseRequest {

    @ApiModelProperty("skuId")
    private Integer skuId;
}
