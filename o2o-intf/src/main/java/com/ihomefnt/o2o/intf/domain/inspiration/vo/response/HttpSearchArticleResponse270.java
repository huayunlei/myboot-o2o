/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article270;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 *
 */
@Data
public class HttpSearchArticleResponse270 {
	
	private List<Article270> articleList;//当前页文章集合
	
	private int artcletotal;//灵感文章总数
}
