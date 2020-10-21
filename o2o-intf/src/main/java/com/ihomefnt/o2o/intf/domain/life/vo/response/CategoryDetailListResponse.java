package com.ihomefnt.o2o.intf.domain.life.vo.response;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBasePageResponse;
import com.ihomefnt.o2o.intf.domain.life.dto.ArticleDto;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/9/28 0028.
 */
@Data
public class CategoryDetailListResponse extends HttpBasePageResponse {

    /**
     * 类目详情页情景图
     */
    private String categoryImgUrl;

    /**
     * 文章列表
     */
    private List<ArticleDto> articleList;
}
