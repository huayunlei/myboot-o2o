package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 指令下发操作
 * 指令内容wiki地址：http://wiki.ihomefnt.com/pages/viewpage.action?pageId=21269846
 */
@Data
@ApiModel("指令下发操作")
public class SendRequest extends HttpBaseRequest {


    @ApiModelProperty("操作人 ID 前端不需要传")
    private Integer operator;
    @ApiModelProperty("指令")
    private String directive;
    @ApiModelProperty("要发送的数据")
    private String data;

    private Integer productId = 1;
    private Integer serviceId = 0;
    private Integer serviceType = 0;
}
