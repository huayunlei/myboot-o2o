package com.ihomefnt.o2o.intf.domain.life.vo.response;

import com.ihomefnt.o2o.intf.domain.life.dto.ReplyDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liyonggang
 * @create 2018-11-02 9:57
 */
@Data
@ApiModel("生活板块文章评论返回数据")
public class LifeCommentListResponse implements Serializable {
    private static final long serialVersionUID = -2754235594112959244L;
    @ApiModelProperty("评论id")
    private Long commentId;
    @ApiModelProperty("评论人")
    private Integer userId;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("权益等级")
    private Long level;
    @ApiModelProperty("时间")
    private String time;
    @ApiModelProperty("是否是用户自己 true是 false 不是")
    private boolean isMe;
    @ApiModelProperty("回复列表")
    private List<ReplyDto> replyList;
    @ApiModelProperty("评论内容跟")
    private String content;
    @ApiModelProperty("是否已经点赞 true是 false 不是")
    private boolean isPraised;
    @ApiModelProperty("点赞数量")
    private Long countPraised;
    @ApiModelProperty("文章id")
    private Long articleId;
}