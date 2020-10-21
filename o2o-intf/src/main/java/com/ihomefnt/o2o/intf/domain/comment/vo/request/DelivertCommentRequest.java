package com.ihomefnt.o2o.intf.domain.comment.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-11 下午3:27
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
@ApiModel(value="DelivertCommentRequest",description="APP4.0服务点评请求参数")
public class DelivertCommentRequest extends HttpBaseRequest {

    //订单编号
    @ApiModelProperty("订单编号")
    private String orderId;

    //订单编号
    @ApiModelProperty("是全软装 还是 全品家 true全软 false全品家")
    private boolean isSoft;
}
