package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-04-23 14:46
 */
@ApiModel("查询订单预算价格与合同模板")
@Data
public class QueryPreSignRequest extends DraftInfoRequest {

    @ApiModelProperty(value = "草稿id")
    private String draftId;

    @ApiModelProperty("操作类型 1 保存草稿并下单 2 草稿id下单")
    private Integer opType;
}
