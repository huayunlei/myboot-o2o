package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 画屏信息查询
 *
 * @author liyonggang
 * @create 2018-12-04 15:58
 */
@Data
@ApiModel("画屏信息查询")
public class ScreenQueryRequest extends HttpBaseRequest implements Serializable {

    private static final long serialVersionUID = 6589278108089657111L;

    @ApiModelProperty("设备id")
    private Integer deviceId;
    @ApiModelProperty("页码")
    private Integer pageNo = 1;
    @ApiModelProperty("页长")
    private Integer pageSize = 10;
    //用户id
    @ApiModelProperty(hidden = true)
    private Integer userId;
    //操作人
    @ApiModelProperty(hidden = true)
    private Integer operator;
    //资源类型 ，枚举
    @ApiModelProperty(hidden = true)
    private Integer resourceType;
    @ApiModelProperty("查询的设备状态 0:下线 1在线 不传为所有设备，查询在线是查询已绑定设备中在线的设备")
    private Integer facilityState;
    @ApiModelProperty("查询类型 1：查询已绑定设备")
    private Integer queryType;
}
