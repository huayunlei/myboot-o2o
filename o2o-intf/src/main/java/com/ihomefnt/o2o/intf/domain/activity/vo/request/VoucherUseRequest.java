package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/11/26
 */
@Data
@ApiModel("商品可用券信息请求")
public class VoucherUseRequest extends HttpBaseRequest {

    @ApiModelProperty("商品Id")
    private Integer productId;
}
