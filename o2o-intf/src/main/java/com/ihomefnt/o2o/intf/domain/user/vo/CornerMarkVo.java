package com.ihomefnt.o2o.intf.domain.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角标信息
 *
 * @author liyonggang
 * @create 2019-02-21 16:23
 */
@Data
@ApiModel("角标信息")
@Accessors(chain = true)
public class CornerMarkVo {
    @ApiModelProperty("角标样式")
    private String cornerMarkStyle;

    @ApiModelProperty("角标内容")
    private String cornerMark;

    @ApiModelProperty("对应资源id")
    private Integer id;

}
