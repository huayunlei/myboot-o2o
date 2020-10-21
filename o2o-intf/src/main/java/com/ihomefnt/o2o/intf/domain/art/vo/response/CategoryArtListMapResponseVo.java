package com.ihomefnt.o2o.intf.domain.art.vo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryArtListMapResponseVo {
	
	private List<CategoryArtListResponse> categoryArtList;

}
