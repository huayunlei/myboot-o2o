package com.ihomefnt.o2o.intf.domain.inspiration.dto;

import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class WeiXinArticle {
    private Long articleId;
	
    private String headFigure;

	private String articleTitle;
	
    private String articleUrl;
	
	private String classify;//分类

    private String createTime;

    /**
     * 
     */
    public WeiXinArticle(Article article) {
        this.articleId = article.getArticleId();
        this.headFigure = article.getHeadFigure();
        this.articleTitle = article.getArticleTitle();
        this.articleUrl = article.getArticleContent();
        this.classify = article.getClassify();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = time.format(article.getCreateTime());
    }

}
