package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

import java.util.List;

/**
 * 艺术品搜索页面推荐关键字
 * @author ZHAO
 */
@Data
public class ArtWord {
	private int typeId;  //类别ID
	
	private String key;  //类别名称：艺术品名称、艺术家
	
	private List<String> words;  //推荐关键字
}
