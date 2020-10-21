package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */
@Data
@ApiModel("方案效果列表查询入参")
@Accessors(chain = true)
public class SolutionListRequest extends HttpBaseRequest {

    @ApiModelProperty("户型id")
    @Deprecated
    /**
     * 用apartmentId
     */
    private Integer houseTypeId;

    @ApiModelProperty("户型id")
    private Integer apartmentId;

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("已选方案id")
    private Long solutionId;

    @ApiModelProperty("查询类型 1:查询专属方案草稿列表,2:查询邻居家方案列表")
    private Integer queryType = 1;

    @ApiModelProperty("选配类型 0:正常选方案,1:虚拟选方案")
    private Integer opType = 0;

    @ApiModelProperty("版本号标记 1不露出已下线方案 2露出已下线方案")
    private Integer version = 1;

}
