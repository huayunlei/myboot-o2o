/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.ArticleComment270;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 *
 */
@Data
public class HttpArticleResponse270 {

	private Article270 article;// 文章详情

	private List<ArticleComment270> commentList;// 文章评论集合

	private int commentTotal;// 文章评论总数

	private List<Article270> recommendArticleList;// 推荐文章集合
}
