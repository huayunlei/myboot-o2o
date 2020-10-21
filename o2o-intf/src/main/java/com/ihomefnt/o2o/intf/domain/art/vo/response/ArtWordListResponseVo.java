package com.ihomefnt.o2o.intf.domain.art.vo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 艺术品搜索页面推荐关键字
 * @author ZHAO
 */
@Data
@AllArgsConstructor
public class ArtWordListResponseVo {
	
	private List<String> words;  //推荐关键字
	
}
