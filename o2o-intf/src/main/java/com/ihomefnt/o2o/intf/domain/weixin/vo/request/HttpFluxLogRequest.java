/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.weixin.vo.request;

import lombok.Data;

/**
 * @author zhang <br/>
 *         用户领取流量记录 <br/>
 *
 */
@Data
public class HttpFluxLogRequest {

	private String unionId;// 微信的unionId

	private String mobile;// 用户手机号码
}
