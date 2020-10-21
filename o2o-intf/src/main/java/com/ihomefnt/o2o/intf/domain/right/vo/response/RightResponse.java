package com.ihomefnt.o2o.intf.domain.right.vo.response;


import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.right.dto.GradeClassifyDto;
import com.ihomefnt.o2o.intf.domain.right.dto.ShareDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("权益查询返回")
@Data
public class RightResponse {

    @ApiModelProperty("权益详情")
    private List<GradeClassifyDto> gradeList;

    @ApiModelProperty("订单权益升级（概览）图片地址")
    private String gradeOverViewPicUrl;

    @ApiModelProperty("权益升级标准")
    private JSONObject gradeUpgradeStandard;


    private ShareDto gradeShareInfo;

    @ApiModelProperty("权益版本号 1老版本 2新版本")
    private Integer versionFlag = 2;

    private Boolean isCustomRightVersion = Boolean.FALSE;

}
