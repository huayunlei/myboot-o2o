package com.ihomefnt.o2o.intf.manager.constant.program;

import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import org.apache.commons.lang3.StringUtils;

public enum StyleEnum {
	AMERICAN(4, "美式", StaticResourceConstants.STYLE_AMERICAN_NEW_IMG),
	MODERN(5, "现代", StaticResourceConstants.STYLE_MODERN_IMG),
	EUROPEAN(6, "欧式", StaticResourceConstants.STYLE_EUROPEAN_IMG),
	CHINESE(7, "中式", StaticResourceConstants.STYLE_CHINESE_IMG);
	
	private Integer code;
	
	private String name;
	
	private String imgUrl;

	StyleEnum(Integer code, String name, String imgUrl) {
		this.code = code;
		this.name = name;
		this.imgUrl = imgUrl;
	}

	public static String getImgUrlByName(String name){
		if(StringUtils.isBlank(name)){
			return "";
		}
		for (StyleEnum styleEnum : StyleEnum.values()) {
			if(styleEnum.getName().equals(name)){
				return styleEnum.getImgUrl();
			}
		}
		return "";
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
