package com.ihomefnt.o2o.intf.domain.right.vo.response;

import com.ihomefnt.o2o.intf.domain.right.dto.RightClassifyDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-12-30 14:08
 */
@ApiModel("2020新版权益")
@Data
public class RightInfoResponse {

    @ApiModelProperty("装修补贴头图")
    private String decorationSubsidyHeadImage;

    @ApiModelProperty("补贴说明文案")
    private String decorationSubsidyInfo;

    @ApiModelProperty("装修补贴示例图片")
    private String decorationSubsidyExamplePic;

    @ApiModelProperty("基本权益")
    private RightClassifyDetail jiBenQuanYi;

    @ApiModelProperty("是否交清全款 0否 1是 2超过应付金额")
    private Integer allMoney = 0;

}
