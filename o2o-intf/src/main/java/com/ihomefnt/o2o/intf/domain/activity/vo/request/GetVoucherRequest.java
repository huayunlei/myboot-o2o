package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/11/26
 */
@Data
@ApiModel("画屏领券参数")
public class GetVoucherRequest extends HttpBaseRequest {

    @ApiModelProperty("邀请码")
    private String inviteCode;
}
