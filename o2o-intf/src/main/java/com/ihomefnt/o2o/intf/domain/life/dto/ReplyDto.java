package com.ihomefnt.o2o.intf.domain.life.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author liyonggang
 * @create 2018-11-02 10:50
 */
@Data
@ApiModel("回复信息")
public class ReplyDto {
    @ApiModelProperty("回复id")
    private Integer replyId;
    @ApiModelProperty("回复人id")
    private Integer userId;
    @ApiModelProperty("回复人名称")
    private String userName;
    @ApiModelProperty("时间")
    private Long time;
    @ApiModelProperty("回复内容")
    private String content;
    @ApiModelProperty("时间字符")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;
    @ApiModelProperty("评论id")
    private Integer commentId;
    @ApiModelProperty("是否是作者 true:是 ,false 否")
    private boolean isAuthor;
}