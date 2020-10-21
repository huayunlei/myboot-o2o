package com.ihomefnt.o2o.intf.domain.activity.vo.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 活动信息
 * @author ZHAO
 */
@Data
public class ActivityInfoResponse {
	private Integer endFlag = 0;//活动是否结束标志：0未结束  1已结束
	
	private Integer publishFlag = 0;//是否发表过文章标志：0未发表  1已发表

	private List<ArticleInfoResponse> winningResultList = Lists.newArrayList();//中奖结果
}
