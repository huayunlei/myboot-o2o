package com.ihomefnt.o2o.intf.dao.inspiration;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article;

public interface ArticleDao {
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
     * @param count
     * @return
     */
    List<Article> queryAllArticle(Integer count);
    
    int addArticleRead(Long articleId);
    
    List<Long> queryArticleByUrl(String articleUrl);
    
    Article queryArticleDetailByUrl(String articleUrl);
}
