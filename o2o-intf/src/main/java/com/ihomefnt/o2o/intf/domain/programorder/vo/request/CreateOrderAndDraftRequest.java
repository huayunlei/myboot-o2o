package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 创建订单请求输入
 * 
 * @author wanyunxin
 */
@ApiModel("创建订单并保存草稿")
@Data
public class CreateOrderAndDraftRequest extends DraftInfoRequest {

    @ApiModelProperty("操作类型 1 新建订单 2 更新订单")
    private Integer type = 1;

    @ApiModelProperty("房产id")
    private Integer houseId;

    @ApiModelProperty("草稿可见当前价格")
    private BigDecimal draftLatestTotalPrice;

    @ApiModelProperty("是否进行了换免费赠品的操作 0 否 1是")
    private Integer onceReplaceFlag = 0;

}
