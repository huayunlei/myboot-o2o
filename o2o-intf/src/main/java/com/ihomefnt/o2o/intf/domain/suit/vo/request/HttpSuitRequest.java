/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * @author zhang<br/> 
 * 套装请求对象<br/>
 */
@Data
public class HttpSuitRequest extends HttpBaseRequest {

	private Long suitId;// 套装ID
}
