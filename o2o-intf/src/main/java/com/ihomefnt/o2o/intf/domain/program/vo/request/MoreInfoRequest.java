package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-02-28 17:19
 */
@ApiModel("软硬装查看更多")
@Data
public class MoreInfoRequest extends HttpBaseRequest {

    @ApiModelProperty("空间ID")
    private Integer spaceId;

    @ApiModelProperty("默认skuId")
    private Integer skuId;

    @ApiModelProperty("furnitureType")
    private Integer furnitureType;

    @ApiModelProperty("已选skuId")
    private Integer selectedSkuId;

    @ApiModelProperty("家具类型 0其他，1:床,2:床垫")
    private Integer itemType;
}
