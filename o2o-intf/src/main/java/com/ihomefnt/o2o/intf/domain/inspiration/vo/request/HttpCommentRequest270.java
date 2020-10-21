/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.inspiration.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * @author zhang
 *
 */
@Data
public class HttpCommentRequest270 extends HttpBaseRequest {
	
	private Long articleId;//文章id

	private String comment;//评论内容

}
