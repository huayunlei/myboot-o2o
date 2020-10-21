package com.ihomefnt.o2o.intf.domain.homepage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/7/17
 */
@Data
@ApiModel("首页banner")
@Accessors(chain = true)
public class Banner {

    @ApiModelProperty("头图")
    private String headImage;

    @ApiModelProperty("链接地址")
    private String linkUrl;

    @ApiModelProperty("参数")
    private String param;

}
