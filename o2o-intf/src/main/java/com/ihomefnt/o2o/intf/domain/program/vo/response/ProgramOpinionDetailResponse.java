package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-05 15:27
 */
@Data
@ApiModel("方案意见详情")
@Accessors(chain = true)
public class ProgramOpinionDetailResponse {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("dolly记录id")
    private Integer reviseOpinionId;

    @ApiModelProperty("wcm记录id")
    private String programOpinionId;

    @ApiModelProperty("状态 1-待提交，2待确认，3:设计中，4:已完成")
    private Integer status;//状态

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("手机号码")
    private String mobile; // 手机号码

    @ApiModelProperty("来源 1:舒克app，2:betaApp")
    private Integer source; // 来源

    @ApiModelProperty("提交人userId")
    private Integer createUserId;

    @ApiModelProperty("添加时间")
    private Date addTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("创建人id")
    private Integer submitterUserId;

    public Integer getUserId() {
        return userId == null ? submitterUserId : userId;
    }

    public Integer getSubmitterUserId() {
        return submitterUserId == null ? userId : submitterUserId;
    }
}
