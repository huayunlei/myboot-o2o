package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author wanyunxin
 * @create 2019-03-01 16:48
 */
@ApiModel("硬装选配包简易信息")
@Data
public class HardItemSimpleSelection {

    @ApiModelProperty("选配包id")
    private Integer hardSelectionId;

    @ApiModelProperty("可选工艺列表")
    private List<HardProcessSimple> hardProcessList;

}
