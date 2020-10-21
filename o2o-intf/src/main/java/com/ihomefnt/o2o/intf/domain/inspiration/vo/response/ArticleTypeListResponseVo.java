package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.KeyValue;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleTypeListResponseVo {
	
	private List<KeyValue> articleTypeList;

}
