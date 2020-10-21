package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-02-28 19:26
 */
@ApiModel("硬硬装查看更多")
@Data
public class HardMoreRequest extends HttpBaseRequest {
    @ApiModelProperty("空间ID")
    private Integer spaceId;

    @ApiModelProperty("默认skuId")
    private Integer hardItemId;

    @ApiModelProperty("默认商品工艺id")
    private Integer craftId;

    @ApiModelProperty("分类id")
    private Integer classifyId;

    @ApiModelProperty("类目id")
    private List<Integer> categoryIdList;

    @ApiModelProperty("已选工艺id")
    private Integer selectedCraftId;

    @ApiModelProperty("已选skuId")
    private Integer selectedHardItemId;

    @ApiModelProperty("bom查询参数")
    private BomQueryMoreRequest bomParam;

    @ApiModelProperty("页码")
    private Integer pageNo;

    @ApiModelProperty("页长")
    private Integer pageSize;

    @Data
    public static class BomQueryMoreRequest {

        @ApiModelProperty("默认组合id")
        private int defaultGroupId;

        @ApiModelProperty("家具类型")
        private int furnitureType;

        @ApiModelProperty("已选组合id")
        private int selectedGroupId;
    }
}
