package com.ihomefnt.o2o.intf.domain.art.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-16 11:16
 */
@ApiModel("查询商品简单信息")
@Data
@NoArgsConstructor
public class ArtworkOrderRequest {

    @ApiModelProperty("商品id or 艺术品规格id")
    private List<String> productIdList;

    @ApiModelProperty("是否查询快照 true是  false否")
    private boolean snapshotFlag = false;

    @ApiModelProperty("订单ID  查询快照 true时存在")
    private Integer orderId;

    @ApiModelProperty("订单编号  查询快照 true时存在")
    private String orderNum;


    public ArtworkOrderRequest(List<String> productIdList) {
        this.productIdList = productIdList;
    }

    public ArtworkOrderRequest(List<String> productIdList, boolean snapshotFlag, Integer orderId, String orderNum) {
        this.productIdList = productIdList;
        this.snapshotFlag = snapshotFlag;
        this.orderId = orderId;
        this.orderNum = orderNum;
    }
}
