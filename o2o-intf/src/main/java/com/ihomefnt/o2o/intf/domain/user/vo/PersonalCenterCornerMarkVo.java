package com.ihomefnt.o2o.intf.domain.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 个人中心热数据，包括用户昵称头像和入口的角标
 *
 * @author liyonggang
 * @create 2019-02-21 16:18
 */
@Data
@ApiModel("个人中心热数据")
public class PersonalCenterCornerMarkVo {
    @ApiModelProperty("用户头像")
    private String userImgUrl;

    @ApiModelProperty("用户昵称")
    private String userNickName;

    @ApiModelProperty("用户手机号")
    private String mobile;

    @ApiModelProperty("艾积分数量")
    private BigDecimal aiIntegral;

    @ApiModelProperty("经纪人手机号")
    private String agentMobile;

    @ApiModelProperty("资源列表")
    private List<CornerMarkVo> resourceList;

}
