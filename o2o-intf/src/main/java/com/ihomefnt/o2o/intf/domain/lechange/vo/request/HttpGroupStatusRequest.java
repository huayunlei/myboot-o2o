/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月16日
 * Description:HttpGroupRequest.java
 */
package com.ihomefnt.o2o.intf.domain.lechange.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhang
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("根据不同条件请求设备参数")
public class HttpGroupStatusRequest extends HttpBaseRequest {

    @ApiModelProperty("设备分组ID")
    private Long groupId;

    @ApiModelProperty("状态：0-离线，1-在线,-1:全部")
    private Integer status;

    @ApiModelProperty("第几页")
    private Integer pageNo;

    @ApiModelProperty("每页多少数据")
    private Integer pageSize;

    @ApiModelProperty("查询魔看的的页码")
    private Integer mokanPageNo = 0;

}
