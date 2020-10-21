package com.ihomefnt.o2o.intf.domain.product.doo;


import lombok.Data;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class UserInspirationFavorites {
    private Long inspirationId;
    private String name;
	private String headImage;
	private String images;
	private String caseSize;
    private String styleName;
    private int readCount;
    private String designer;
    
	public String getCaseSize() {
		if(null != caseSize && caseSize.endsWith(".0")){
			return caseSize.replace(".0", "");
		}
		return caseSize;
	}
}
