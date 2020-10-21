/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * @author charv
 *
 */
@Data
public class ChangeAvatarRequestVo extends HttpBaseRequest{
	
	private String newAvatar; //用户头像url

}
