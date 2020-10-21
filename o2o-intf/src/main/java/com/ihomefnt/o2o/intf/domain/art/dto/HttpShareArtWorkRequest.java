/**   
* @Title: HttpShareArtWorkRequest.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月20日 下午4:18:30 
* @version V1.0   
*/
package com.ihomefnt.o2o.intf.domain.art.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**   
 * @Title: HttpShareArtWorkRequest.java 
 * @Description: TODO
 * @author Charl 
 * @date 2016年7月20日 下午4:18:30 
 * @version V1.0   
 */
@Data
public class HttpShareArtWorkRequest extends HttpBaseRequest {
	
	private Long shareId;  //艺术品id
	
	private int typeId; //类型id 1浏览,2购买,3喜欢,4分享
}
