package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import com.ihomefnt.o2o.intf.domain.art.dto.Content;
import lombok.Data;

/**
 * DNA分享
 * @author ZHAO
 */
@Data
public class DnaShareResponse {
	 private Content shareContent;//微信分享
	 
	 private String mUrl; //h5链接

	public String getmUrl() {
		return mUrl;
	}

	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	 
}
