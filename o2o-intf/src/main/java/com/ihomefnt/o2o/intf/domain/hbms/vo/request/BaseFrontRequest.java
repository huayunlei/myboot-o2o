package com.ihomefnt.o2o.intf.domain.hbms.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created 2017/7/11
 *
 * @author gaoxin
 */
@Data
@ApiModel(description = "基础前端请求vo")
public class BaseFrontRequest {

    @ApiModelProperty(value = "操作人id")
    protected Integer operateUserId = 0;
    
    @ApiModelProperty(value = "操作机器IP")
    protected String ip;
}
