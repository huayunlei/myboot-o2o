/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.ad.vo.request;

import com.ihomefnt.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * @author weitichao
 *
 */
@Data
public class AdStartPageRequestVo extends HttpBaseRequest {
	
	private Long picId;  //本地缓存图片的id
	
	private Long groupId;  //本地缓存图片的groupId

}
