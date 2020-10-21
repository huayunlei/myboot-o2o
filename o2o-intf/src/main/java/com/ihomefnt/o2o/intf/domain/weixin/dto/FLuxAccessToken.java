/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.weixin.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;

/**
 * @author zhang
 *
 */
@Data
public class FLuxAccessToken {

	@JsonIgnore
	private Long accessId;// 主键

	private String accessToken;//accessToken

	@JsonIgnore
	private Timestamp createTime;//请求时间

}
