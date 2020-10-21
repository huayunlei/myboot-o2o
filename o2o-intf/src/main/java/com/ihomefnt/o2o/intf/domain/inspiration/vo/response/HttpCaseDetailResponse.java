package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.comment.dto.UserCommentDto;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Case;
import lombok.Data;

import java.util.List;
@Data
public class HttpCaseDetailResponse {
	
	private Case caseInfo;
	private List<UserCommentDto> userCommentList;
	private int commentCount;
	private int collection;
	private String urlType;
	private String UA;
	private boolean enable;
	private String title1;
	private String icon1;
	private String desc;
}
