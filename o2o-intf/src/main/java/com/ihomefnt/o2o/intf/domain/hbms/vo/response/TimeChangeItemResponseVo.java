package com.ihomefnt.o2o.intf.domain.hbms.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/9/26
 */
@Data
@Accessors(chain = true)
public class TimeChangeItemResponseVo {

    @ApiModelProperty("项目id")
    private Integer itemId;

    @ApiModelProperty("项目名称")
    private String itemName;
}
