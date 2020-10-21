package com.ihomefnt.o2o.intf.dao.inspiration;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.ArticleComment270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Case;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.KeyValue;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Picture270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureAlbum;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureInfo;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Strategy;
import com.ihomefnt.o2o.intf.domain.product.vo.response.AppButton;

public interface InspirationDao {
	
    List<AppButton> queryPhotoButton();
	
	List<AppButton> queryStrategyButton();
	
	List<Long> queryPictureAlbumIds(Map<String, Object> param);
	
	List<PictureAlbum> queryPictureAlbumList(Map<String, Object> param);
	
	PictureAlbum queryPictureAlbumView(Map<String, Object> param);
	
	int queryPictureAlbumCount(Map<String, Object> param);
	
	List<Strategy> queryStrategyList(Map<String, Object> param);
	
	List<Strategy> queryStrategyHomeList(Map<String, Object> param);
	
	Strategy queryStrategyDetail(Map<String, Object> param);
	
	int queryStrategyCount(Map<String, Object> param);
	
	List<Case> queryCaseList(Map<String, Object> param);
	
	Case queryCaseDetail(Map<String, Object> param);
	
	int queryCaseCount(Map<String, Object> param);
	
	int updateAlbumViewCount(Map<String, Object> param);
	
	int updateStrategyViewCount(Map<String, Object> param);
	
	int updateCaseViewCount(Map<String, Object> param);
	
    int updateAlbumTranspondCount(Map<String, Object> param);
	
	int updateStrategyTranspondCount(Map<String, Object> param);
	
	int updateCaseTranspondCount(Map<String, Object> param);
	
	List<String> queryCondition(String menuKey);

	List<PictureInfo> queryPictureByAlbumId(Long albumId);
	
	/**
	 * 获取所有文章栏目
	 * @return
	 */
	List<KeyValue>	getArticleTypeList270();
	
	/**
	 * 先取是置顶并且 满足没有过去三天 时间倒序的2条数据
	 * @return
	 */
	List<Article270> getDataByTopAndRemain(Map<String, Object> params);
	
	/**
	 * 如果 不存在置顶数据，则根据时间倒序取两条就可以
	 * @return
	 */
	List<Article270> getDataByTime(Map<String, Object> params);
	
	/**
	 * 查询除去前面两篇文章以外的文章
	 * @return
	 */
	List<Article270> getDataByCondition(Map<String, Object> params);
	
	/**
	 * 通过灵感id获取灵感文章
	 * @param articleId
	 * @return
	 */
	Article270 getArticleByPK270(Long articleId);
	
	/**
	 * 通过灵感Id获取文章评论列表
	 * @return
	 */
	List<ArticleComment270> getArticleCommentListByArticleId270(Map<String,Object> paramMap);
	
	/**
	 * 通过灵感Id获取文章评论总数
	 * @param articleId
	 * @return
	 */
	int getArticleCommentTotalByArticleId270(Long articleId);
	
	/**
	 * 通过灵感Id获取该灵感文章的推荐列表
	 * @param sourceId
	 * @return
	 */
	List<Article270> getRecommendArticleListBySourceId(Long sourceId);
	
	/**
	 * 根据标题搜索灵感文章
	 * @param title
	 * @return
	 */
	List<Article270> searhArticleList(Map<String, Object> paramMap);
	
	/**
	 * 根据标题搜索灵感文章总数
	 * @param title
	 * @return
	 */
	int searhArticleListTotal(String title);
	

	
	/**
	 * 查询该用户点赞该文章的次数
	 * @return
	 */
	int getPraiseArticleCountByArticleIdAndUserId(Map<String,Object> paramMap);
	
	/**
	 * 操作类型：阅读1,2点赞,3收藏,4转发
	 * @return
	 */
	int updateArticleOpByArticleId(Map<String,Object> paramMap);
	
	/**
	 * 插入点赞日志
	 * @return
	 */
	int insertPraiseLog(Map<String,Object> paramMap);
	
	/**
	 * 插入点赞日志
	 * @return
	 */
	int addComment(Map<String,Object> paramMap);
	
	/**
	 * 获取三张美图
	 * @return
	 */
	List<Picture270> getPictureList();
}
