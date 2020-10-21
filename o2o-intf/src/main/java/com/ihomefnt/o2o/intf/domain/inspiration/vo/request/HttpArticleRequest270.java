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
public class HttpArticleRequest270 extends HttpBaseRequest {

	private Long articleId;// 文章id

	private Integer pageNo;// 当前页数

	private Integer pageSize;// 每页大小
	
	private Integer fromType;//app请求为1， H5为0， 默认0

}
