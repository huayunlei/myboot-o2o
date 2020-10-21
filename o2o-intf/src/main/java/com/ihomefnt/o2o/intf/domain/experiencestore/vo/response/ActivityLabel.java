/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.experiencestore.vo.response;

import lombok.Data;

/**
 * 体验店活动信息
 * @author weitichao
 *
 */
@Data
public class ActivityLabel {
    private Long experStoreActivityId;    //体验店活动ID
	
	private String title; //活动标题
	
	private String desc; //活动描述
	
	private String h5Url; //活动的H5页面
	
	private Long experStoreId;  //体验店ID
	
	private String h5ActivityContent; //活动的H5文字版内容  本次新增字段
}
