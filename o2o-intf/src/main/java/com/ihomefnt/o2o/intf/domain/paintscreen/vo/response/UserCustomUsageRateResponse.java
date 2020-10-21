package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户个人空间使用率信息
 *
 * @author liyonggang
 * @create 2018-12-04 16:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户个人空间使用率信息")
public class UserCustomUsageRateResponse implements Serializable {

    private static final long serialVersionUID = -2438461129972270168L;

    @ApiModelProperty("总数")
    private Integer sum;
    @ApiModelProperty("已使用数")
    private Integer used;
}
