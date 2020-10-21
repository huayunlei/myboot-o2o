package com.ihomefnt.o2o.intf.service.life;


import com.ihomefnt.o2o.intf.domain.life.dto.LifeHomePage;
import com.ihomefnt.o2o.intf.domain.life.vo.request.CategoryRequestVo;
import com.ihomefnt.o2o.intf.domain.life.vo.request.ArticleRequestVo;
import com.ihomefnt.o2o.intf.domain.life.vo.response.ArticleDetailResponse;
import com.ihomefnt.o2o.intf.domain.life.vo.response.CategoryDetailListResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

public interface LifeService {


	CategoryDetailListResponse getCategoryInfoList(CategoryRequestVo request);

    ArticleDetailResponse getArticleInfo(ArticleRequestVo request);

    Integer addBrowse(ArticleRequestVo request);

    Integer addArticlePraise(ArticleRequestVo request);

    Integer addForward(ArticleRequestVo request);

    LifeHomePage getLifeHomePage(HttpBaseRequest request);
}
