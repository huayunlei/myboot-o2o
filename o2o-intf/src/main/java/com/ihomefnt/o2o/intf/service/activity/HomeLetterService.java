package com.ihomefnt.o2o.intf.service.activity;

import com.ihomefnt.o2o.intf.domain.activity.vo.request.*;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.ActivityInfoResponse;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.ArticleInfoResponse;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.PublishArticleResponseVo;
import com.ihomefnt.oms.trade.util.PageModel;

/**
 * 1219活动 三行家书
 * @author ZHAO
 */
public interface HomeLetterService {
	/**
	 * 发表文章
	 * @param request
	 * @return
	 */
	PublishArticleResponseVo publishArticle(PublishArticleRequest request);
	
	/**
	 * 投票
	 * @param request
	 * @return
	 */
	void homeLetterVote(HomeLetterVoteRequest request);
	
	/**
	 * 查询投票记录（分页）
	 * @param request
	 * @return
	 */
	PageModel queryVoteRecordList(QueryVoteRecordRequest request);
	
	/**
	 * 查询所有投稿文章列表（分页）
	 * @param request
	 * @return
	 */
	PageModel queryArticleList(ArticleListRequest request);
	
	/**
	 * 查询用户投稿信息
	 * @param request
	 * @return
	 */
	ArticleInfoResponse queryArticleInfo(ArticleInfoRequest request);
	
	/**
	 * 查询活动信息
	 * @param request
	 * @return
	 */
	ActivityInfoResponse queryActivityInfo(ActivityInfoRequest request);
}
