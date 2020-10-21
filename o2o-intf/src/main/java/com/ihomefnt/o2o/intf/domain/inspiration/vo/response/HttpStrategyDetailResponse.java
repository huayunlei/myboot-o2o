package com.ihomefnt.o2o.intf.domain.inspiration.vo.response;

import com.ihomefnt.o2o.intf.domain.comment.dto.UserCommentDto;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Strategy;
import lombok.Data;

import java.util.List;

@Data
public class HttpStrategyDetailResponse {
	
	private Strategy strategy;
	private List<UserCommentDto> userCommentList;
	private int commentCount;
	private int collection;
	private String urlType;
	private String UA;  //ua信息
	private boolean enable;  
	private String title1;  //攻略标题
	private String icon1;  //攻略图标
	private String desc;  //攻略描述
}
