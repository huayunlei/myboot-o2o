package com.ihomefnt.o2o.intf.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 草稿信息
 *
 * @author liyonggang
 * @create 2019-02-27 12:42
 */
@ApiModel("草稿信息")
@Data
public class OrderDraftDto {

    @ApiModelProperty("草稿id")
    private String draftId;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("选方案进度")
    private BigDecimal operationProgress;

    @ApiModelProperty("草稿内容")
    private String draftContent;

    @ApiModelProperty("草稿类型：0预选方案  1调整设计")
    private Integer draftType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
