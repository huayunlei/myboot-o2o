package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/19 21:10
 */
@Data
@ApiModel("CollageLogistVo")
public class CollageLogistVo {

    @ApiModelProperty("物流公司id")
    private Integer id;

    @ApiModelProperty("物流公司名称")
    private String name;

    @ApiModelProperty("物流编号")
    private String logistNum;

    @ApiModelProperty("物流状态更新明细")
    private List<String> logistList;

    @ApiModelProperty("物流单状态")
    private Integer status;
}
