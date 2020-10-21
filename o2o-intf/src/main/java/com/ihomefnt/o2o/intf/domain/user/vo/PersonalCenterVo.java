package com.ihomefnt.o2o.intf.domain.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页个人中心主体数据响应
 *
 * @author liyonggang
 * @create 2019-02-21 16:06
 */
@Data
@ApiModel("首页个人中心主体数据响应")
public class PersonalCenterVo {

    @ApiModelProperty("用户中心")
    private ResourceVo userCenter;

    @ApiModelProperty("广告")
    private ResourceVo brands;

    @ApiModelProperty("功能列表")
    private ResourceVo functions;

    @ApiModelProperty("顶部菜单")
    private ResourceVo topMenu;


}
