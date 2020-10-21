package com.ihomefnt.o2o.intf.domain.art.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.Content;
import lombok.Data;

/**
 * 艺术品分享
* @Title: HttpArtworkShareResponse.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月22日 下午2:32:17 
* @version V1.0
 */
@Data
public class HttpArtworkShareResponse {
	
	 private Content shareContent;//微信分享
	 
	 private String mUrl; //h5链接

	public String getmUrl() {
		return mUrl;
	}

	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	 
	 
	
}
