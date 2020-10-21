package com.ihomefnt.o2o.intf.domain.life.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/27 0027.
 */
@Data
@ApiModel("文章列表")
public class ArticleListRequestVo extends HttpBaseRequest {
    private int page = 1;
    private int limit = 10;
}
