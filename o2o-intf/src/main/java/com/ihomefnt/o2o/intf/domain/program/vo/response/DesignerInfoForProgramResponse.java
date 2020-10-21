/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月5日
 * Description:DesignerInfoForProgramResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class DesignerInfoForProgramResponse implements Serializable {
	

	/**
	 * 设计师id
	 */
	private Integer designerId;

	/**
	 * 设计师头像
	 */
	private String headImgUrl;

	/**
	 * 设计师名称
	 */
	private String designerName;
	
	/**
	 * 设计师标签
	 */
	private String designerLables;
	
	/**
	 * 设计师背图
	 */
	private String desingerBackImage;
	
	/**
	 * 设计空间集合
	 */
	private List<String> roomList;
	
	/**
	 * 是否是主设计师:true是,false否
	 */
	private Boolean mainDesignerTag;

	public Boolean isMainDesignerTag() {
		return mainDesignerTag;
	}

	public void setMainDesignerTag(Boolean mainDesignerTag) {
		this.mainDesignerTag = mainDesignerTag;
	}
}
