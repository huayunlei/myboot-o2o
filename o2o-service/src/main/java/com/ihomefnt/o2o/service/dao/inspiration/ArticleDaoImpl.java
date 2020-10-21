package com.ihomefnt.o2o.service.dao.inspiration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.inspiration.ArticleDao;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article;
@Repository
public class ArticleDaoImpl implements ArticleDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.inspiration.ArticleDao.";
	@Override
	public List<Article> queryArticle(String articleType) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryArticle", articleType);
	}

	@Override
	public Article queryArticleById(Long articleId) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryArticleById", articleId);
	}

    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.intf.article.dao.ArticleDao#queryArticle(java.lang.Integer)
     */
    @Override
    public List<Article> queryAllArticle(Integer count) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryAllArticle", count);
    }
    
    public int addArticleRead(Long articleId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("articleId", articleId);
        return sqlSessionTemplate.insert(NAME_SPACE + "addArticleRead", map);
    }

	@Override
	public List<Long> queryArticleByUrl(String articleUrl) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("articleUrl", articleUrl);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryArticleByUrl", map);
	}
	
	@Override
	public Article queryArticleDetailByUrl(String articleUrl) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("articleUrl", articleUrl);
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryArticleDetailByUrl", map);
	}
	
}
