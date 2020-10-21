package com.ihomefnt.o2o.intf.domain.inspiration.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class HttpArticleRequest extends HttpBaseRequest {
    private Long articleId;
    private String articleUrl;
}
