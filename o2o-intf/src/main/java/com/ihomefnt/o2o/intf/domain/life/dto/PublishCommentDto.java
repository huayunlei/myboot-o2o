package com.ihomefnt.o2o.intf.domain.life.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发布评论信息
 *
 * @author liyonggang
 * @create 2018-11-02 11:06
 */
@Data
@ApiModel("评论信息")
public class PublishCommentDto extends HttpBaseRequest implements Serializable {

    private static final long serialVersionUID = 2557505559910111964L;
    @ApiModelProperty(value = "id", required = true)
    private Long id;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(hidden = true)
    private Long userId;
    @ApiModelProperty(hidden = true)
    private String userName;
    @ApiModelProperty(hidden = true)
    private String headUrl;

}
