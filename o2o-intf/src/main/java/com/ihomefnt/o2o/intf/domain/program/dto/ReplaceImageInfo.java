package com.ihomefnt.o2o.intf.domain.program.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 替换空间信息
 * Author: ZHAO
 * Date: 2018年6月1日
 */
@Data
public class ReplaceImageInfo implements Serializable {
	private String pictureURL = "";//空间首图

	private List<String> imgList = Lists.newArrayList();//空间其他图片集合
}