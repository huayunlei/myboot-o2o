package com.ihomefnt.o2o.intf.domain.right.vo.response;

import com.ihomefnt.o2o.intf.domain.right.dto.RightClassifyDetail;
import com.ihomefnt.o2o.intf.domain.right.dto.ShareDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("权益详情返回")
@Data
public class RightDetailResponse {

    @ApiModelProperty("权益等级详情页标题")
    private String gradeLevelTitle;
    @ApiModelProperty("权益等级图片地址（钻石图）")
    private String gradeLevelPicUrl;
    @ApiModelProperty("底图")
    private String gradeLevelBackPicUrl;
    @ApiModelProperty("权益名称图")
    private String gradelLevelUrl;
    @ApiModelProperty("权益名称")
    private String gradelLevelName;
    @ApiModelProperty("全品家立减特权权益详情")
    private RightClassifyDetail liJianTeQuan;
    @ApiModelProperty("天降喜福")
    private RightClassifyDetail tianJiangXiFu;
    @ApiModelProperty("情义无价")
    private RightClassifyDetail qingYiWuJia;
    @ApiModelProperty("基本权益")
    private RightClassifyDetail jiBenQuanYi;
    @ApiModelProperty("专属权益")
    private RightClassifyDetail exclusiveRights;
    @ApiModelProperty("分享内容")
    private ShareDto gradeShareInfo;
    @ApiModelProperty("权益版本号 1老版本 2新版本")
    private Integer versionFlag = 2;

}
