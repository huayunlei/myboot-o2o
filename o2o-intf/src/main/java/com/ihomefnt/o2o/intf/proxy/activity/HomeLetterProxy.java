package com.ihomefnt.o2o.intf.proxy.activity;

import com.ihomefnt.o2o.intf.domain.activity.dto.HomeLetterListDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.HomeLetterWinningDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.PublishArticleResultDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.VoteRecordListDto;
import com.ihomefnt.o2o.intf.domain.activity.dto.HomeLetterVo;

import java.util.Map;

/**
 * 1219活动  三行家书
 * @author ZHAO
 */
public interface HomeLetterProxy {
	/**
	 * 发表文章
	 * @param params
	 * @return
	 */
	PublishArticleResultDto publishArticle(Map<String, Object> params);
	
	/**
	 * 投票
	 * @param params
	 * @return
	 */
	Long homeLetterVote(Map<String, Object> params);
	
	/**
	 * 查询投票记录（分页）
	 * @param params
	 * @return
	 */
	VoteRecordListDto queryVoteRecordList(Map<String, Object> params);
	
	/**
	 * 查询用户投稿信息
	 * @param params
	 * @return
	 */
	HomeLetterVo queryArticleInfo(Map<String, Object> params);
	
	/**
	 * 查询所有投稿文章列表（分页）
	 * @param params
	 * @return
	 */
	HomeLetterListDto queryArticleList(Map<String, Object> params);
	
	/**
	 * 查询三行家书中奖结果
	 * @return
	 */
	HomeLetterWinningDto queryWinningResult();
}
