package com.ihomefnt.o2o.intf.domain.life.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2018/9/27 0027.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@ApiModel(value = "文章请求参数")
public class ArticleRequestVo extends HttpBaseRequest {
    @ApiModelProperty("文章id")
    private Integer articleId;
    @ApiModelProperty("用户id")
    private Integer userId;
}
