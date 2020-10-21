package com.ihomefnt.o2o.intf.domain.art.vo.response;

import lombok.Data;

/**
 * 艺术品专题信息
 * @author ZHAO
 */
@Data
public class ArtSubjectResponse {
	private Integer subjectId;//专题ID
	
	private String subjectTitle;//专题标题
	
	private String subjectSubTitle;//专题副标题
	
	private String bannerUrl;//专题banner
	
	private String skipUrl;//跳转地址
}
