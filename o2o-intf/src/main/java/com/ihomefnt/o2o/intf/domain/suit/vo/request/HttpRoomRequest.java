/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * @author zhang<br/>
 * 空间请求对象<br/>
 *
 */
@Data
public class HttpRoomRequest extends HttpBaseRequest {

	private Long roomId;// 空间ID<由于空间和套装为一一对应,故不需要套装ID>

}
