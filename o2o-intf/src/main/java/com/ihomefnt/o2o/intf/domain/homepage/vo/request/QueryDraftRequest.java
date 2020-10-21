package com.ihomefnt.o2o.intf.domain.homepage.vo.request;

import com.ihomefnt.o2o.intf.domain.designer.vo.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@ApiModel("查询方案草稿入参")
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class QueryDraftRequest extends PageRequest {
	@ApiModelProperty("订单号")
	private Integer orderId;
	
	@ApiModelProperty("草稿ID")
	private String draftId;

    @ApiModelProperty("草稿编号")
    private String draftProfileNum;

    @ApiModelProperty("选方案进度")
    private BigDecimal draftProgress;

    @ApiModelProperty("草稿类型：0预选方案  1调整设计")
    private Integer draftType;

    @ApiModelProperty("排序类型：0草稿箱排序  1对比列表排序")
    private Integer sortType = 0;

    @ApiModelProperty("查询类型 0：正常查询，1：查询签约草稿")
    private Integer queryType = 0;

    public QueryDraftRequest(Integer orderId, String draftProfileNum) {
        this.orderId = orderId;
        this.draftProfileNum = draftProfileNum;
    }
}
