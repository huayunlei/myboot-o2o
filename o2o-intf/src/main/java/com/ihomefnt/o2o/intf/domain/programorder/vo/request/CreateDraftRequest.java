package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */

@ApiModel("提交选方案草稿入参")
@Data
@Accessors(chain = true)
public class CreateDraftRequest extends HttpBaseRequest{

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("草稿内容json")
    private String draftJsonStr;

    @ApiModelProperty("选方案进度")
    private BigDecimal draftProgress;

    @ApiModelProperty("草稿类型：0预选方案  1调整设计")
    private Integer draftType;


}
