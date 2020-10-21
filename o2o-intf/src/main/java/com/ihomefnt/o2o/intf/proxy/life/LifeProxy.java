package com.ihomefnt.o2o.intf.proxy.life;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.life.dto.ArticleDto;
import com.ihomefnt.o2o.intf.domain.life.dto.CategoryDto;
import com.ihomefnt.o2o.intf.domain.life.dto.LifeHomePage;
import com.ihomefnt.o2o.intf.domain.life.dto.LifePraiseDto;
import com.ihomefnt.o2o.intf.domain.life.vo.request.CategoryRequestVo;
import com.ihomefnt.o2o.intf.domain.life.vo.request.ArticleRequestVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

/**
 * Created by Administrator on 2018/9/27 0027.
 */
public interface LifeProxy {
	Integer getPraiseCount(Integer userId, Integer articleId);

    /**
     * 新增点赞记录
     * @param lifePraise
     */
    void addLifePraise(LifePraiseDto lifePraise);

    /**
     * 点赞数+1，并返回当前大晒的现在点赞数
     * @param request
     * @return
     */
    Integer addPraise(ArticleRequestVo request);

    /**
     * 浏览次数增加
     * @param request
     * @return
     */
    Integer addBrowse(ArticleRequestVo request);

    /**
     * 转发次数增加
     * @param request
     * @return
     */
    Integer addForward(ArticleRequestVo request);

    ArticleDto getArticleById(ArticleRequestVo request);

    List<LifePraiseDto> getLifePraiseListByUserId(Integer userId);

    CategoryDto getCategoryById(Map<String, Object> params);

    PageResponse<ArticleDto> getArticleList(CategoryRequestVo request);

    LifeHomePage getLifeHomePage(HttpBaseRequest request);
}
