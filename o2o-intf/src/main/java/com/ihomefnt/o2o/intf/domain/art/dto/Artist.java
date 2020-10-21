package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 艺术家
* @Title: Artist.java 
* @Description: TODO
* @author Charl 
* @date 2016年7月18日 下午3:50:37 
* @version V1.0
 */
@Data
public class Artist implements Serializable{

	private static final long serialVersionUID = -4637834519656193493L;
	private long artistId; //艺术家主键id
	
	private String name; //艺术家姓名
	
	private int gendar; //艺术家性别:0未知,1男,2女
	
	private String brief; //艺术家简介
	
	private String avast; //艺术家头像
	
	private int artworkCount; //艺术品数量
	
	private List<String> experience; //艺术家经历
	
	private List<String> selfDesc;//艺术家创作自述内容  只有艺术家有，品牌无此项
	 
	private List<Artwork> artworkList; //艺术家艺术品列表
	
	private Integer artistType; //艺术家类型：1.艺术家 2.品牌
	
	private String title; //页面头部信息 title
	
	private String selfExp; //个人经历 title
	
	private String createStatement; //创作自述 title
}
