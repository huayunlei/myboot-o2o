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
public class HttpInspirationRequest270 extends HttpBaseRequest {

	private Integer articleType;// 文章类型：动态传入

	private Integer pageNo;// 当前页数

	private Integer pageSize;// 每页大小
}
