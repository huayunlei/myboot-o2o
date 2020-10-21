package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2019-12-12 15:58
 */
@Data
public class AppGroupDetailResponseVo {

    @ApiModelProperty("组id")
    private Integer groupId;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("组名称")
    private String name;

    @ApiModelProperty("组下面的所有app")
    private List<AppGroupResponseVo> app;
}
