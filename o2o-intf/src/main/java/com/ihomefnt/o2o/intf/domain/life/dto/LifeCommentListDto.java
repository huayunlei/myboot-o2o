package com.ihomefnt.o2o.intf.domain.life.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取生活板块评论请求参数
 *
 * @author liyonggang
 * @create 2018-11-01 17:49
 */
@Data
@ApiModel("获取生活板块评论请求参数")
public class LifeCommentListDto extends HttpBaseRequest implements Serializable {
    private static final long serialVersionUID = -7974745042724838156L;
    @ApiModelProperty(value = "页码")
    private Integer pageNo = 0;

    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize = 3;

    @ApiModelProperty(value = "文章id", required = true)
    private Long articleId;
}
