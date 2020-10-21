package com.ihomefnt.o2o.intf.domain.art.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class KeywordVo {
	private int typeId = 0;  //类别ID
	
	private String key = "";  //类别名称：艺术品名称、艺术家
	
	private List<String> words = Lists.newArrayList();  //推荐关键字
}
