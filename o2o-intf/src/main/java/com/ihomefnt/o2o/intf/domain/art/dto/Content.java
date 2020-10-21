/**   
* @Title: Content.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月22日 下午2:33:33 
* @version V1.0   
*/
package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

/** 分享内容
 * @Title: Content.java 
 * @Description: TODO
 * @author Charl 
 * @date 2016年7月22日 下午2:33:33 
 * @version V1.0   
 */
@Data
public class Content {
	
	private String title;// 标题
	
	private String desc;// 描述
	
	private String url;// 要分享的url
}
