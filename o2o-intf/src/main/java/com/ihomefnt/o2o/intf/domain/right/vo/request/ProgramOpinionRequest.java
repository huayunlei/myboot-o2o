package com.ihomefnt.o2o.intf.domain.right.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * 方案意见查询参数
 *
 * @author liyonggang
 * @create 2019-08-08 16:00
 */
@Data
@ApiModel("方案意见查询参数")
@Accessors(chain = true)
public class ProgramOpinionRequest {

    @ApiModelProperty("方案意见唯一id")
    private String id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("方案id")
    private Integer solutionId;

    @ApiModelProperty("方案名称")
    private String solutionName;

    @ApiModelProperty("方案头图")
    private String solutionImage;

    @ApiModelProperty("装修类型 0硬装+软装  1纯软装")
    private Integer decorationType;//装修类型 0硬装+软装  1纯软装

    @ApiModelProperty("dolly记录id")
    private Integer reviseOpinionId;

    @ApiModelProperty("方案类型名称")
    private String solutionTypeName;

    @ApiModelProperty("wcm记录id")
    private String programOpinionId;

    @ApiModelProperty("风格名称")
    private String solutionStyleName;// 风格名称

    @ApiModelProperty("状态 1-待提交，2待确认，3:设计中，4:已完成")
    private Integer status;//状态

    private Integer orderNum;

    private Integer pageNo;

    private Integer pageSize;

    @ApiModelProperty("验证码")
    private String authCode;

    @ApiModelProperty("手机号码")
    private String mobile; // 手机号码

    @ApiModelProperty("来源 1:舒克app，2:betaApp")
    private Integer source; // 来源

    @ApiModelProperty("提交人userId")
    private Integer createUserId;

    private Integer orderId;//兼容前后端

    public Integer getOrderNum() {
        return orderNum == null ? orderId : orderNum;
    }

    public Integer getOrderId() {
        return orderId == null ? orderNum : orderId;
    }

    public String getId() {
        return StringUtils.isNotBlank(id) ? id : StringUtils.isNotBlank(programOpinionId) ? programOpinionId : reviseOpinionId == null ? null : reviseOpinionId.toString();
    }

    public Integer getReviseOpinionId() {
        return reviseOpinionId != null ? reviseOpinionId : status != null && (status.equals(3) || status.equals(4)) ? id == null ? null : Integer.parseInt(id) : null;
    }

    public String getProgramOpinionId() {
        return programOpinionId != null ? programOpinionId : status != null && (status.equals(1) || status.equals(2)) ? id : null;
    }
}
