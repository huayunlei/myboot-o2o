package com.ihomefnt.o2o.intf.domain.main.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/26
 */

@ApiModel("首页提示信息")
@Data
@Accessors(chain = true)
public class MainPageTips {

    @ApiModelProperty("确认方案顶部提示文案")
    private String confirmTopTips;

}
