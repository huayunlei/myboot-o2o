package com.ihomefnt.o2o.intf.domain.sku.vo.response;

import com.ihomefnt.o2o.intf.domain.sku.vo.request.SkuDetailQueryRequestVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liyonggang
 * @create 2019-06-26 10:48
 */
@Data
@ApiModel("skuList")
@Accessors(chain = true)
public class QuerySkuDetailListResponseVo {

    private SkuDetailQueryRequestVo  requestVo;

    private QuerySkuDetailResponseVo responseVo;
}
