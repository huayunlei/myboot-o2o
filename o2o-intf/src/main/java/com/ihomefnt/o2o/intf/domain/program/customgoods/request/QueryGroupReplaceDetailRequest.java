package com.ihomefnt.o2o.intf.domain.program.customgoods.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-03-20 20:55
 */
@Data
@ApiModel("组合替换详情查询")
public class QueryGroupReplaceDetailRequest extends HttpBaseRequest {

    @ApiModelProperty("默认组合id")
    private Integer defaultGroupId;

    @ApiModelProperty("替换后组合id")
    private Integer replaceGroupId;

    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("默认组合数量,用于商品中心计算总差价")
    private Integer defaultGroupNum;

    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
    private Integer freeAble = 0;
}
