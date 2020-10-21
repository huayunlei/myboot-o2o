package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 审核信息
 *
 * @author liyonggang
 * @create 2018-12-05 12:57
 */
@Data
@ApiModel("审核信息")
public class AuditRequest extends HttpBaseRequest implements Serializable {

    private static final long serialVersionUID = 2803226788422947945L;

    @ApiModelProperty("设备绑定ID")
    private Integer bindId;
    @ApiModelProperty("审核的状态 1通过 3驳回")
    private Integer status;
}
