/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * @author zhang
 *
 */
@Data
public class ChangeNickRequestVo extends HttpBaseRequest {
	
	  private String newNick;//新昵称
}
