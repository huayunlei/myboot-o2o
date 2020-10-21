package com.ihomefnt.o2o.intf.domain.delivery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xuxiaohao
 * @Date: 2018/6/5
 */
@Data
@ApiModel("商品状态返回")
public class ProductStatusListVo implements Serializable {

    @ApiModelProperty("商品唯一编号")
    private String superKey;

    @ApiModelProperty("商品状态")
    private Integer status;

    @ApiModelProperty("商品状态名称")
    private String statusName;
}
