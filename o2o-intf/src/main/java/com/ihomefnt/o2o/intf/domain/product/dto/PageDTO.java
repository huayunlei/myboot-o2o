/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: Charl
 * Date: 2017/7/18
 * Description:PageVo.java
 */
package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页包装对象
 *
 * @author wangmin
 * @since 2019-04-11
 */
@Data
@ApiModel("分页包装信息")
public class PageDTO<T> {

    @ApiModelProperty("页码")
    private Integer pageNo = 1;

    @ApiModelProperty("每页记录数")
    private Integer pageSize = 10;

    @ApiModelProperty("总记录数")
    private Long totalRecords;

    @ApiModelProperty("数据集合")
    private List<T> list = new ArrayList<>();

}
