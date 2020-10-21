package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/18 10:24 上午
 */
@Data
@ApiModel("草稿请求参数")
public class DeleteDraftRequest extends HttpBaseRequest {

    @ApiModelProperty("草稿ID")
    private String draftId;

    @ApiModelProperty("草稿编号")
    private Long draftProfileNum;

    @ApiModelProperty("草稿类型：0预选方案  1调整设计")
    private Integer draftType;

    @ApiModelProperty("订单编号")
    private Long orderNum;
    
}
