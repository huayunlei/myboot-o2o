package com.ihomefnt.o2o.intf.service.inspiration;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article;


public interface ArticleService {
	/**
	 * 加载对新的列表
	 * @param articleType
	 * @return
	 */
	List<Article> queryArticle(String articleType);
	/**
	 * 根据ID加载文章
	 * @param articleId
	 * @return
	 */
	Article queryArticleById(Long articleId);

    /**
     * 临时加载新的列表
     * @param count
     * @return
     */
    public List<Article> queryAllArticle(Integer count);
    
    Boolean addArticleRead(String articleUrl);
    
    Article queryArticleDetailByUrl(String articleUrl);
}
