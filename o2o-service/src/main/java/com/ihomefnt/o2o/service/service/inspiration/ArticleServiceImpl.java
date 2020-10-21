package com.ihomefnt.o2o.service.service.inspiration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.dao.inspiration.ArticleDao;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article;
import com.ihomefnt.o2o.intf.service.inspiration.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleDao articleDao;
	@Override
	public List<Article> queryArticle(String articleType) {
		return articleDao.queryArticle(articleType);
	}

    public List<Article> queryAllArticle(Integer count) {
        return articleDao.queryAllArticle(count);
    }

	@Override
	public Article queryArticleById(Long articleId) {
		return articleDao.queryArticleById(articleId);
	}

    @Override
    public Boolean addArticleRead(String articleUrl) {
    	List<Long> ids = articleDao.queryArticleByUrl(articleUrl);
    	if(null != ids && ids.size()>0) {
            int i = articleDao.addArticleRead(ids.get(0));
            return i == 1;
    	}
    	return false;
    }

	@Override
	public Article queryArticleDetailByUrl(String articleUrl) {
		return articleDao.queryArticleDetailByUrl(articleUrl);
	}

}
