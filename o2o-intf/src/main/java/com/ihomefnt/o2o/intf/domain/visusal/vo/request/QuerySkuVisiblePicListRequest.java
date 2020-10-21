package com.ihomefnt.o2o.intf.domain.visusal.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 蒋军
 */
@ApiModel("查询空间可视化图片入参")
@Data
public class QuerySkuVisiblePicListRequest extends HttpBaseRequest {
    @ApiModelProperty("方案id")
    private Integer solutionId;

    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("skuID列表")
    private List<Integer> skuIdList;

    @ApiModelProperty(value = "替换后新的硬装sku")
    private List<ReplaceSkuInfoVo> replaceHardSkuIds;

    @Data
    @ApiModel("硬装数据")
    public static class ReplaceSkuInfoVo {

        @ApiModelProperty("硬装skuId")
        private Integer replaceSkuId;

        @ApiModelProperty("硬装工艺id")
        private Integer replaceCraftId;
    }
}
